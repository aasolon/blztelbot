package com.rtm.blztelbot.service;

import com.google.common.base.Splitter;
import com.rtm.blztelbot.service.civ6.Civ6Service;
import com.rtm.blztelbot.service.civ6.PlayerDurationsResult;
import com.rtm.blztelbot.telegrambot.BlzTelBot;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.time.Duration;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BlzTelBotService {

    private static final String STATS_COMMAND = "/stats";

    @Lazy
    @Autowired
    private BlzTelBot blzTelBot;

    @Lazy
    @Autowired
    private Civ6Service civ6Service;

    public void processAnyUpdate(Update update) {
        String text = update.getMessage().getText();
        if (text.startsWith(STATS_COMMAND + " ")) {
            processStatsCommand(update.getMessage().getChatId(), text);
        }
    }

    public void processAdminUpdate(Update update) {
        String text = update.getMessage().getText();
        if (text.startsWith("/sendmsg ")) {
            processSendMsg(text);
        }
        if (text.startsWith(STATS_COMMAND + " ")) {
            processStatsCommand(update.getMessage().getChatId(), text);
        }
    }

    public void sendMessageToMe(String text) {
        long chatId = Long.parseLong(System.getenv("TELEGRAM_ADMIN_CHAT_ID"));
        for(final String token : Splitter.fixedLength(4000).split(text)) {
            if (StringUtils.isNotEmpty(token)) {
                blzTelBot.sendMessage(chatId, token);
            }
        }
    }

    public void sendMessageToChatId(long chatId, String text) {
        for(final String token : Splitter.fixedLength(4000).split(text)) {
            if (StringUtils.isNotEmpty(token)) {
                blzTelBot.sendMessage(chatId, token);
            }
        }
    }

    private void processSendMsg(String text) {
        text = text.replace("/sendmsg ", "");
        if (text.startsWith("chatid=")) {
            text = text.replace("chatid=", "");
            String chatId = StringUtils.substringBefore(text, " ");
            text = text.replace(chatId + " ", "");
            if (text.startsWith("msg=")) {
                text = text.replace("msg=", "");
                String msg = StringUtils.substringBefore(text, null);
                if (StringUtils.isNotEmpty(msg)) {
                    sendMessageToChatId(Long.parseLong(chatId), msg);
                }
            }
        }
    }

    public void processStatsCommand(Long chatId, String text) {
        text = text.replace(STATS_COMMAND + " ", "");
        long hours;
        try {
            hours = Long.parseLong(text);
        } catch (NumberFormatException ex) {
            return;
        }

        PlayerDurationsResult playerDurationsResult = civ6Service.calcPlayerDurations(hours);
        Map<String, Duration> playerDurations = playerDurationsResult.getPlayerDurations();
        // сортируем в порядке убывания затраченного на ход времени
        Map<String,Duration> sortedPlayerDurations = playerDurations.entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        String msg;
        if (!sortedPlayerDurations.isEmpty()) {
            StringBuilder msgBuilder = new StringBuilder("Статистика за последние " + hours + " hours\n" +
                    "Сделано ходов:  " + playerDurationsResult.getTurnsCount() + "\n" +
                    "Предположительное потраченное игроками время на ходы:");
            for (Map.Entry<String, Duration> playerDurationEntry : sortedPlayerDurations.entrySet()) {
                Duration duration = playerDurationEntry.getValue();
                msgBuilder
                        .append("\n")
                        .append(String.format("*%10s*",playerDurationEntry.getKey()))
                        .append(": ")
                        .append(String.format("%d days %02d hours %02d minutes", duration.toDays(), duration.toHoursPart(), duration.toMinutesPart()));
                if (duration.toDays() >= 1) {
                    msgBuilder.append(" (\uD83D\uDE31)");
                }
            }
            msg = msgBuilder.toString();
        } else {
            msg = "Данные за последние " + hours + " hours не найдены \uD83E\uDD37\u200D♂️";
        }
        sendMessageToChatId(chatId, msg);
    }
}
