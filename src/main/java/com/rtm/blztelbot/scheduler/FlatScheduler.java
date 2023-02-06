package com.rtm.blztelbot.scheduler;

import com.rtm.blztelbot.service.FlatService;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;

//@Service
public class FlatScheduler {

    private final FlatService flatService;

    public FlatScheduler(FlatService flatService) {
        this.flatService = flatService;
    }

    @Scheduled(cron = "0 0/5 * * * ?")
    public void crawl() throws IOException {
        flatService.refreshFlats();
    }
}
