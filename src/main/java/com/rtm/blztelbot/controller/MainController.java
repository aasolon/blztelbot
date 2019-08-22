package com.rtm.blztelbot.controller;

import com.rtm.blztelbot.bot.BlzTelBot;
import com.rtm.blztelbot.oauth2.OAuth2FlowHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class MainController {

    @Autowired
    private BlzTelBot blzTelBot;

    @Autowired
    private OAuth2FlowHandler oAuth2FlowHandler;

    @RequestMapping("/wakeup")
    public void wakeUp() {
        blzTelBot.sendMessage("wakeup");
    }

    @RequestMapping("/test")
    public String test(@RequestParam String param) throws IOException {
        oAuth2FlowHandler.getToken();
        return "received: " + param;
    }
}
