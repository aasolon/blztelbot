package com.rtm.blztelbot.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WakeupScheduler {

    private final RestTemplate herokuRestTemplate;

    public WakeupScheduler(RestTemplate herokuRestTemplate) {
        this.herokuRestTemplate = herokuRestTemplate;
    }

    @Scheduled(cron = "0 0/5 * * * ?")
    public void wakeUp() {
        herokuRestTemplate.getForEntity("https://blztelbot.herokuapp.com/wakeup", String.class);
    }
}
