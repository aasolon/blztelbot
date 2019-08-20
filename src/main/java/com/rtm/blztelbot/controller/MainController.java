package com.rtm.blztelbot.controller;

import com.rtm.blztelbot.bot.BlzTelBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @Autowired
    private BlzTelBot blzTelBot;

    @RequestMapping("/test")
    public String test() {
        blzTelBot.sendMessage("Refresh");
        return "test";
    }
}
