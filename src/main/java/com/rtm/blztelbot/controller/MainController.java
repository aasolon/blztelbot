package com.rtm.blztelbot.controller;

import com.rtm.blztelbot.blizzardapi.oauth2.OAuth2FlowHandler;
import com.rtm.blztelbot.telegrambot.BlzTelBot;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
public class MainController {

    @Autowired
    private BlzTelBot blzTelBot;

    @Autowired
    private OAuth2FlowHandler oAuth2FlowHandler;

    @RequestMapping("/wakeup")
    public void wakeUp() {
        blzTelBot.sendMessageToMe("wakeup1");
    }

    @RequestMapping("/test")
    public String test(@RequestParam String param) throws IOException {
        oAuth2FlowHandler.getToken();
        return "received: " + param;
    }

    private static long lastStepTime;

    @RequestMapping(value = "/civ6", method = RequestMethod.POST)
    public void test(@RequestBody Map<String, String> params) {
        try {
            String timePassed = lastStepTime != 0 ? getTimePassed() : null;
            lastStepTime = System.currentTimeMillis();
            String playerName = params.get("value2");
            String stepNumber = params.get("value3");
            String msg = "Эй, " + playerName + ", ходи уже (ход " + stepNumber + ")" +
                    (timePassed != null ? "\nПрошло времени с последнего хода: " + timePassed : "");
            blzTelBot.sendMessageToMe(msg);
        } catch (Exception e) {
            blzTelBot.sendMessageToMe(ExceptionUtils.getStackTrace(e));
        }
    }

    private String getTimePassed() {
        long currentTime = System.currentTimeMillis();
        long timePassed = currentTime - lastStepTime;
        long dy = TimeUnit.MILLISECONDS.toDays(timePassed);
        long hr = TimeUnit.MILLISECONDS.toHours(timePassed)
                - TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(timePassed));
        long min = TimeUnit.MILLISECONDS.toMinutes(timePassed)
                - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(timePassed));
        long sec = TimeUnit.MILLISECONDS.toSeconds(timePassed)
                - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timePassed));
        long ms = TimeUnit.MILLISECONDS.toMillis(timePassed)
                - TimeUnit.SECONDS.toMillis(TimeUnit.MILLISECONDS.toSeconds(timePassed));
        return String.format("%d Days %d Hours %d Minutes %d Seconds %d Milliseconds", dy, hr, min, sec, ms);
    }
}
