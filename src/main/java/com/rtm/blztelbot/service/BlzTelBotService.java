package com.rtm.blztelbot.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rtm.blztelbot.model.GameInfo;
import com.rtm.blztelbot.telegrambot.BlzTelBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.io.IOException;

@Service
public class BlzTelBotService {

    public static final String COMMAND_ADMIN_GAME_INFO = "/addgameinfo";

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BlzTelBot blzTelBot;

    public void processMsg(Message message) {

    }

    public void processAdminMsg(Message message) {
        if (message.hasText()) {
            String text = message.getText();
            if (text.startsWith(COMMAND_ADMIN_GAME_INFO)) {
                String payload = text.substring(COMMAND_ADMIN_GAME_INFO.length() + 1);
                try {
                    GameInfo gameInfo = objectMapper.readValue(payload, GameInfo.class);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void sendMessageToMe(String text) {
        long chatId = Long.parseLong(System.getenv("TELEGRAM_ADMIN_CHAT_ID"));
        blzTelBot.sendMessage(chatId, text);
    }
}
