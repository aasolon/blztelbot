package com.rtm.blztelbot.scheduler;

import com.rtm.blztelbot.telegrambot.BlzTelBot;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
public class WakeupService {

    private final BlzTelBot blzTelBot;

    public WakeupService(BlzTelBot blzTelBot) {
        this.blzTelBot = blzTelBot;
    }

    public void sendWakeUpMessageToMe(String text) {
        long chatId = Long.parseLong(System.getenv("TELEGRAM_ADMIN_CHAT_ID"));
        blzTelBot.sendMessage(chatId, text);
    }
}
