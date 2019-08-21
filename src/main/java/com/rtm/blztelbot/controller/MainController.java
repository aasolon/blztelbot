package com.rtm.blztelbot.controller;

import com.rtm.blztelbot.bot.BlzTelBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @Autowired
    private BlzTelBot blzTelBot;

    @RequestMapping("/wakeup")
    public void wakeUp() {
        blzTelBot.sendMessage("wakeup");
    }

    @RequestMapping("/test")
    public String test(@RequestParam String param) {
        return "received: " + param;
    }
}
