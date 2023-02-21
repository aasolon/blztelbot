package com.rtm.blztelbot.telegrambot;

import com.rtm.blztelbot.service.BlzTelBotService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 * 128316795  - a
 * 5053544603 - t
 * -601860434 - test civ6 chat
 * -1001189608207 - civ6 chat
 */
@Component
public class BlzTelBot extends TelegramLongPollingBot {

    private static final Logger log = LoggerFactory.getLogger(BlzTelBot.class);

    public static final long CIV6_GROUP_CHAT_ID = -1001189608207L;

    @Value("${TELEGRAM_ADMIN_CHAT_ID}")
    private long adminChatId;

    @Value("${TELEGRAM_BOT_USERNAME}")
    private String botUsername;

    @Value("${TELEGRAM_BOT_TOKEN}")
    private String botToken;

    @Autowired
    private BlzTelBotService blzTelBotService;

//    public BlzTelBot(DefaultBotOptions botOptions) {
//        super(botOptions);
//    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            logUpdate(update);
            Long userId = update.getMessage().getFrom().getId();
            if (userId == adminChatId) {
                blzTelBotService.processAdminUpdate(update);
            } else {
                blzTelBotService.processAnyUpdate(update);
            }

//            sendEchoMessage(update);
        }
    }

    public void sendMessage(long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);
        message.setParseMode("Markdown");
        try {
            execute(message); // Call method to send the message
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void logUpdate(Update update) {
        Long chatId = update.getMessage().getChatId();
        String userName = update.getMessage().getFrom().getUserName();
        String text = update.getMessage().getText();

        log.debug("""

                        Telegram bot received text message from userName = "{}" in chatId = "{}":
                        {}""",
                userName, chatId, text);
    }

    private void sendEchoMessage(Update update) {
        String message = "recieved " + update.getMessage().getText() + " from @" + update.getMessage().getFrom().getUserName();
        sendMessage(update.getMessage().getChatId(), message);
    }
}
