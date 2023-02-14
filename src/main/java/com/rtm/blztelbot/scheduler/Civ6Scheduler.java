package com.rtm.blztelbot.scheduler;

import com.rtm.blztelbot.service.BlzTelBotService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import static com.rtm.blztelbot.telegrambot.BlzTelBot.TEST_CIV6_CHAT_ID;

@Service
public class Civ6Scheduler {

    private BlzTelBotService blzTelBotService;

    public Civ6Scheduler(BlzTelBotService blzTelBotService) {
        this.blzTelBotService = blzTelBotService;
    }

    @Scheduled(cron = "0 0 20 * * *")
    public void wakeUp() {
        blzTelBotService.processStatsCommand(TEST_CIV6_CHAT_ID, "/stats 24");
    }
}
