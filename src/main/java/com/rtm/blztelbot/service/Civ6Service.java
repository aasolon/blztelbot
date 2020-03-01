package com.rtm.blztelbot.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rtm.blztelbot.dao.Civ6Dao;
import com.rtm.blztelbot.model.Civ6Webhook;
import com.rtm.blztelbot.model.TurnInfoRaw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class Civ6Service {

    @Autowired
    private Civ6Dao civ6Dao;

    @Autowired
    private BlzTelBotService blzTelBotService;

    @Autowired
    private ObjectMapper objectMapper;

    public void addTurnInfo(Civ6Webhook webhook) {
//        String gameName = webhook.getValue1();
//        String playerName = webhook.getValue2();
//        int turnNumber = Integer.parseInt(webhook.getValue3());
//        Date now = new Date();
//
//        Long gameId = civ6Dao.getGameId(gameName);
//        Long playerId = civ6Dao.getPlayerId(playerName);
//        int turnOrder = civ6Dao.getTurnOrder(gameId, playerId);
//
//        civ6Dao.insertNewTurnInfo(gameId, playerId, turnNumber, now);
//
//        long turnNumberOfPreviousTurnInfo = turnOrder == 1 ? turnNumber - 1 : turnNumber;
//        civ6Dao.findPreviousTurnInfo(gameId, turnOrder, turnNumberOfPreviousTurnInfo);
    }

    public void addTurnInfoRaw(Civ6Webhook webhook) {
        String gameName = webhook.getValue1();
        String playerName = webhook.getValue2();
        int turnNumber = Integer.parseInt(webhook.getValue3());
        LocalDateTime now = LocalDateTime.now();

        TurnInfoRaw lastTurnInfoRaw = civ6Dao.findLastTurnInfoRaw(gameName);

        civ6Dao.insertNewTurnInfoRaw(gameName, playerName, turnNumber, now);
        blzTelBotService.sendMessageToMe("Successfully added webhook\n" + getPrettyPrintingWebhook(webhook));

        if (lastTurnInfoRaw != null) { // lastTurnInfoRaw=null only possible when 1st turn in da game
            if (lastTurnInfoRaw.getEndDate() == null) {
                civ6Dao.updateTurnInfoRawEndDate(lastTurnInfoRaw.getId(), now);
                blzTelBotService.sendMessageToMe("Successfully updated END_DATE of last CIV_TURNINFO_RAW (ID = " + lastTurnInfoRaw.getId() + ")");
            } else {
                blzTelBotService.sendMessageToMe("Detected last CIV_TURNINFO_RAW with not null END_DATE (ID = " + lastTurnInfoRaw.getId() + ")");
            }
        }
    }

    private String getPrettyPrintingWebhook(Civ6Webhook webhook) {
        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(webhook);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Cant write webhook object to JSON string", e);
        }
    }
}
