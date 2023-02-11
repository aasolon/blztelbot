package com.rtm.blztelbot.service;

import com.rtm.blztelbot.entity.Civ6Player;
import com.rtm.blztelbot.entity.Civ6TurnInfo;
import com.rtm.blztelbot.model.Civ6Webhook;
import com.rtm.blztelbot.repository.Civ6PlayerRepository;
import com.rtm.blztelbot.repository.Civ6TurnInfoRepository;
import org.springframework.stereotype.Service;

@Service
public class Civ6Service {

    private final BlzTelBotService blzTelBotService;
    private final Civ6PlayerRepository civ6PlayerRepository;

    private final Civ6TurnInfoRepository civ6TurnInfoRepository;

    public Civ6Service(BlzTelBotService blzTelBotService, Civ6PlayerRepository civ6PlayerRepository, Civ6TurnInfoRepository civ6TurnInfoRepository) {
        this.blzTelBotService = blzTelBotService;
        this.civ6PlayerRepository = civ6PlayerRepository;
        this.civ6TurnInfoRepository = civ6TurnInfoRepository;
    }

    public void processCiv6Webhook(Civ6Webhook webhook) {
        String gameName = webhook.getValue1();
        String webhookPlayerName = webhook.getValue2();
        String turnNumber = webhook.getValue3();

        Civ6Player civ6Player = civ6PlayerRepository.findByCivName(webhookPlayerName);
        String playerName = civ6Player != null ? civ6Player.getTelegramName() : webhookPlayerName;

        String message = String.format("Эй, @%s, ходи давай! (игра: %s, ход: %s)", playerName, gameName, turnNumber);
//        blzTelBotService.sendMessageToChatId(-601860434, message);

        Civ6TurnInfo civ6TurnInfo = new Civ6TurnInfo();
        civ6TurnInfoRepository.save(civ6TurnInfo);
    }
}
