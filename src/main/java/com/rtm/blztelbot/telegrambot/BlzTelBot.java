package com.rtm.blztelbot.telegrambot;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class BlzTelBot extends TelegramLongPollingBot {

    private static final Logger log = LoggerFactory.getLogger(BlzTelBot.class);

    @Autowired
    private ObjectMapper objectMapper;

//    @Autowired
//    private BlzTelBotService blzTelBotService;

//    public BlzTelBot(DefaultBotOptions botOptions) {
//        super(botOptions);
//    }

    @Override
    public String getBotUsername() {
        return System.getenv("TELEGRAM_BOT_USERNAME");
    }

    @Override
    public String getBotToken() {
        return System.getenv("TELEGRAM_BOT_TOKEN");
    }

    @Override
    public void onUpdateReceived(Update update) {
        // 5053544603 - t
        // 128316795  - a
        // 2147483647 - max

        try {
            log.debug("Telegram bot received update = " + objectMapper.writeValueAsString(update));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        if (update.hasMessage()) {
            long telegramAdminChatId = Long.parseLong(System.getenv("TELEGRAM_ADMIN_CHAT_ID"));
            Long chatId = update.getMessage().getChatId();
            if (chatId != null && chatId == telegramAdminChatId) {
//                blzTelBotService.processAdminMsg(update.getMessage());
            } else {
//                blzTelBotService.processMsg(update.getMessage());
            }
        }
        if (update.hasMessage() && update.getMessage().hasText()) {
            SendMessage message = new SendMessage();
            message.setChatId(update.getMessage().getChatId());
            message.setText("recieved " + update.getMessage().getText() + " from @" + update.getMessage().getFrom().getUserName());
            try {
                execute(message); // Call method to send the message
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMessage(long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);
        try {
            execute(message); // Call method to send the message
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
