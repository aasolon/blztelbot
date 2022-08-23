package com.rtm.blztelbot.telegrambot;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class BlzTelBot extends TelegramLongPollingBot {

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
            SendMessage message = new SendMessage()
                    .setChatId(update.getMessage().getChatId())
                    .setText("recieved " + update.getMessage().getText());
            try {
                execute(message); // Call method to send the message
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMessage(long chatId, String text) {
        SendMessage message = new SendMessage()
                .setChatId(chatId)
                .setText(text);
        try {
            execute(message); // Call method to send the message
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
