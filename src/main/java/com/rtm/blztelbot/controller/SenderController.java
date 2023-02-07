package com.rtm.blztelbot.controller;

import com.rtm.blztelbot.service.BlzTelBotService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SenderController {

    private final BlzTelBotService blzTelBotService;

    public SenderController(BlzTelBotService blzTelBotService) {
        this.blzTelBotService = blzTelBotService;
    }

    @RequestMapping("/send-message")
    public void wakeUp(@RequestParam long chatId, @RequestParam String msg) {
        blzTelBotService.sendMessageToChatId(chatId, msg);
    }
}
