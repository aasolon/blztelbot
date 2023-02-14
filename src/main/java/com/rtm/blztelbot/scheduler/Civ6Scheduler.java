package com.rtm.blztelbot.scheduler;

import com.rtm.blztelbot.service.BlzTelBotService;
import com.rtm.blztelbot.service.civ6.Civ6Service;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import static com.rtm.blztelbot.telegrambot.BlzTelBot.CIV6_GROUP_CHAT_ID;

@Service
public class Civ6Scheduler {

    private final BlzTelBotService blzTelBotService;

    private final Civ6Service civ6Service;

    public Civ6Scheduler(BlzTelBotService blzTelBotService, Civ6Service civ6Service) {
        this.blzTelBotService = blzTelBotService;
        this.civ6Service = civ6Service;
    }

    @Scheduled(cron = "0 0 20 * * *")
    public void printStatsEveryDay() {
        blzTelBotService.processStatsCommand(CIV6_GROUP_CHAT_ID, "/stats 24");
    }

    @Scheduled(cron = "0 0 19 * * *")
    public void printLifeHackEveryDay() {
        String lifehack = civ6Service.getRandomLifehackForPosting();
        if (lifehack != null) {
            lifehack = "Минутка полезных советов по Civilization 6 ☝️\uD83E\uDD13\n\n" + lifehack;
        blzTelBotService.sendMessageToChatId(CIV6_GROUP_CHAT_ID, lifehack);
//            blzTelBotService.sendMessageToChatId(128316795, lifehack);
        }
    }
}
