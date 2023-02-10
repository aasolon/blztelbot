package com.rtm.blztelbot.service;

import com.google.common.base.Splitter;
import com.rtm.blztelbot.telegrambot.BlzTelBot;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class BlzTelBotService {

    @Lazy
    @Autowired
    private BlzTelBot blzTelBot;

    public void processMsg(Message message) {

    }

    public void processAdminMsg(Update update) {
        String text = update.getMessage().getText();
        if (text.startsWith("/sendmsg")) {
            text = text.replace("/sendmsg ", "");
            if (text.startsWith("chatid=")) {
                text = text.replace("chatid=", "");
                String chatId = StringUtils.substringBefore(text, " ");
                text = text.replace(chatId + " ", "");
                if (text.startsWith("msg=")) {
                    text = text.replace("msg=", "");
                    String msg = StringUtils.substringBefore(text, null);
                    if (StringUtils.isNotEmpty(msg)) {
                        blzTelBot.sendMessage(Long.parseLong(chatId), msg);
                    }
                }
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
