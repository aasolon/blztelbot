package com.rtm.blztelbot.service;

import com.rtm.blztelbot.telegrambot.BlzTelBot;
import org.springframework.stereotype.Service;

@Service
public class WakeupService {

    private final BlzTelBot blzTelBot;

    public WakeupService(BlzTelBot blzTelBot) {
        this.blzTelBot = blzTelBot;
    }

//    private BlzTelBot blzTelBot;
//
//    public WakeupService() {
//    }

    public void sendWakeUpMessageToMe(String text) {
        long chatId = Long.parseLong(System.getenv("TELEGRAM_ADMIN_CHAT_ID"));
        blzTelBot.sendMessage(chatId, text);
    }
}
