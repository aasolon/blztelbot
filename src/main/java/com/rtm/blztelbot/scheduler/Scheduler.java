package com.rtm.blztelbot.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class Scheduler {

    @Autowired
    private RestTemplate restTemplate;

    @Scheduled(cron = "0 0/5 10,11,12,13,14,15,16,17,18,19,20,21,22,23,0,1,2 * * ?")
    public void wakeUp() {
        ResponseEntity<String> result = restTemplate.getForEntity("http://localhost:8080/test", String.class);
        System.out.println(result.getBody());
    }
}
