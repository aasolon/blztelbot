package com.rtm.blztelbot.service;

import com.rtm.blztelbot.entity.Civ6CurrentGame;
import com.rtm.blztelbot.entity.Civ6Player;
import com.rtm.blztelbot.entity.Civ6TurnInfo;
import com.rtm.blztelbot.model.Civ6Webhook;
import com.rtm.blztelbot.repository.Civ6CurrentGameRepository;
import com.rtm.blztelbot.repository.Civ6PlayerRepository;
import com.rtm.blztelbot.repository.Civ6TurnInfoRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.rtm.blztelbot.telegrambot.BlzTelBot.TEST_CIV6_CHAT_ID;

@Service
public class Civ6Service {

    private final BlzTelBotService blzTelBotService;
    private final Civ6PlayerRepository civ6PlayerRepository;
    private final Civ6TurnInfoRepository civ6TurnInfoRepository;
    private final Civ6CurrentGameRepository civ6CurrentGameRepository;

    public Civ6Service(BlzTelBotService blzTelBotService, Civ6PlayerRepository civ6PlayerRepository,
                       Civ6TurnInfoRepository civ6TurnInfoRepository, Civ6CurrentGameRepository civ6CurrentGameRepository) {
        this.blzTelBotService = blzTelBotService;
        this.civ6PlayerRepository = civ6PlayerRepository;
        this.civ6TurnInfoRepository = civ6TurnInfoRepository;
        this.civ6CurrentGameRepository = civ6CurrentGameRepository;
    }

    public void processCiv6Webhook(Civ6Webhook webhook) {
        String webhookGameName = webhook.getValue1();
        String webhookPlayerName = webhook.getValue2();
        String turnNumber = webhook.getValue3();

        Civ6Player civ6Player = civ6PlayerRepository.findByCivName(webhookPlayerName);
        String playerName = civ6Player != null ? civ6Player.getTelegramName() : webhookPlayerName;

        String message = String.format("Эй, @%s, ходи давай! (игра: %s, ход: %s)", playerName, webhookGameName, turnNumber);

        boolean isTestRequest = civ6Player != null && civ6Player.getCivName().startsWith("civ_player_");
        if (!isTestRequest)
            blzTelBotService.sendMessageToChatId(TEST_CIV6_CHAT_ID, message);

        Civ6CurrentGame currentGame = civ6CurrentGameRepository.findFirstByGameName(webhookGameName);
        if (StringUtils.equals(webhookGameName, currentGame.getGameName())) {
            Civ6TurnInfo civ6TurnInfo = new Civ6TurnInfo();
            civ6TurnInfo.setGameName(webhookGameName);
            civ6TurnInfo.setTurnNumber(Long.parseLong(turnNumber));
            civ6TurnInfo.setPlayerName(webhookPlayerName);
            civ6TurnInfoRepository.save(civ6TurnInfo);
        }
    }

    public Map<String, Duration> calcPlayerDurations(long hours) {
        HashMap<String, Duration> result = new HashMap<>();

        List<Civ6Player> activePlayers = civ6PlayerRepository.findAllByActiveTrue();
        Map<String, Civ6Player> playersMap = activePlayers.stream().collect(Collectors.toMap(Civ6Player::getCivName, Function.identity()));

        Instant now = Instant.now();
        Instant nowMinusHours = now.minus(hours, ChronoUnit.HOURS);
        // достаем список ходов за последние N часов
        List<Civ6TurnInfo> turnInfoList = civ6TurnInfoRepository.findAllByCreateDatetimeAfterOrCreateDatetimeEqualsOrderByCreateDatetime(nowMinusHours, nowMinusHours);

        // достаем первый ход, который предшествовал выбранному списку
        Civ6TurnInfo zeroTurnInfo = civ6TurnInfoRepository.findFirstByCreateDatetimeBeforeOrderByCreateDatetimeDesc(nowMinusHours);
        if (zeroTurnInfo != null) {
            Duration zeroPlayerDuration;
            // считаем время между "первый ход, который предшествовал выбранному списку" и "первый ход из списка"
            if (!turnInfoList.isEmpty()) {
                Civ6TurnInfo firstTurnInfo = turnInfoList.get(0);
                if (checkTurnsConnected(zeroTurnInfo, firstTurnInfo, playersMap)) {
                    zeroPlayerDuration = Duration.between(nowMinusHours, firstTurnInfo.getCreateDatetime());
                } else {
                    zeroPlayerDuration = Duration.ZERO;
                }
            }
            // если список пустой, то считаем время между "первый ход, который предшествовал выбранному списку" и "now"
            else {
                zeroPlayerDuration = Duration.between(nowMinusHours, now);
            }
            result.put(zeroTurnInfo.getPlayerName(), zeroPlayerDuration);
        }



        for (int i = 0; i < turnInfoList.size(); i++) {
            Civ6TurnInfo currentTurnInfo = turnInfoList.get(i);
            Duration currentPlayerDuration;
            // считаем время между "текущий ход" и "следующий ход"
            if (i + 1 < turnInfoList.size()) {
                Civ6TurnInfo nextTurnInfo = turnInfoList.get(i + 1);
                currentPlayerDuration = calcConnectedTurnInfoDuration(playersMap, currentTurnInfo, nextTurnInfo);
            }
            // для последнего хода из списка считаем время между "последний ход" и "now"
            else {
                currentPlayerDuration = Duration.between(currentTurnInfo.getCreateDatetime(), now);
            }
            result.compute(currentTurnInfo.getPlayerName(),
                    (k, v) -> v == null ? currentPlayerDuration : v.plus(currentPlayerDuration));
        }
        return result;
    }

    private Duration calcConnectedTurnInfoDuration(Map<String, Civ6Player> playersMap, Civ6TurnInfo currentTurnInfo, Civ6TurnInfo nextTurnInfo) {
        Duration currentPlayerDuration;
        if (checkTurnsConnected(currentTurnInfo, nextTurnInfo, playersMap)) {
            currentPlayerDuration = Duration.between(currentTurnInfo.getCreateDatetime(), nextTurnInfo.getCreateDatetime());
        } else {
            currentPlayerDuration = Duration.ZERO;
        }
        return currentPlayerDuration;
    }

    private boolean checkTurnsConnected(Civ6TurnInfo currentTurnInfo, Civ6TurnInfo nextTurnInfo, Map<String, Civ6Player> playersMap) {
        Civ6Player currentPlayer = playersMap.get(currentTurnInfo.getPlayerName());
        Civ6Player nextPlayer = playersMap.get(nextTurnInfo.getPlayerName());
        if (currentTurnInfo.getTurnNumber() + 1 == nextTurnInfo.getTurnNumber() &&
                currentPlayer.getTurnOrder() == playersMap.size() && nextPlayer.getTurnOrder() == 1) {
            return true;
        }
        if (currentTurnInfo.getTurnNumber().longValue() == nextTurnInfo.getTurnNumber() &&
                currentPlayer.getTurnOrder() + 1 == nextPlayer.getTurnOrder()) {
            return true;
        }
        return false;
    }
}
