package com.rtm.blztelbot.service;

import com.google.common.base.Splitter;
import com.rtm.blztelbot.telegrambot.BlzTelBot;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

@Service
public class BlzTelBotService {

    @Autowired
    private BlzTelBot blzTelBot;

    public void processMsg(Message message) {

    }

    public void processAdminMsg(Message message) {
        if (message.hasText()) {
            String text = message.getText();
            if (text.startsWith("/addgameinfo")) {
            }
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
}
