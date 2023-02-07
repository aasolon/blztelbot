package com.rtm.blztelbot.service;

import com.rtm.blztelbot.model.Civ6Webhook;
import org.springframework.stereotype.Service;

@Service
public class Civ6Service {

    private final BlzTelBotService blzTelBotService;

    public Civ6Service(BlzTelBotService blzTelBotService) {
        this.blzTelBotService = blzTelBotService;
    }

    public void processCiv6Webhook(Civ6Webhook webhook) {
        String gameName = webhook.getValue1();
        String playerName = webhook.getValue2();
        String turnNumber = webhook.getValue3();

        blzTelBotService.sendMessageToChatId(-601860434, "Эй, " + playerName + ", ходи давай!");
    }
}
