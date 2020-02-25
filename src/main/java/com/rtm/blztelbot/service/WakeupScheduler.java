package com.rtm.blztelbot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WakeupScheduler {

    @Autowired
    private RestTemplate restTemplate;

    @Scheduled(cron = "0 0/5 * * * ?")
    public void wakeUp() {
        ResponseEntity<String> result = restTemplate.getForEntity("https://blztelbot.herokuapp.com/wakeup", String.class);
    }
}
