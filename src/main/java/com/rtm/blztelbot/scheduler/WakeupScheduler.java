package com.rtm.blztelbot.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WakeupScheduler {

    @Autowired
    private RestTemplate restTemplate;

    @Scheduled(cron = "0 0/5 * * * ?")
    public void wakeUp() {
        restTemplate.getForEntity("https://blztelbot.herokuapp.com/wakeup", String.class);
    }
}
