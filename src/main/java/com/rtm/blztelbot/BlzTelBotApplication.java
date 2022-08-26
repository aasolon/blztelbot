package com.rtm.blztelbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties(AppConfigProperties.class)
public class BlzTelBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlzTelBotApplication.class, args);
    }
}
