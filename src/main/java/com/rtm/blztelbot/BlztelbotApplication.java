package com.rtm.blztelbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.ApiContext;

/**
 * https://blztelbot.herokuapp.com/
 */
@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties(AppConfig.class)
public class BlztelbotApplication {

    public static void main(String[] args) {
        ApiContextInitializer.init();
        SpringApplication.run(BlztelbotApplication.class, args);
    }

//    @Bean
    public DefaultBotOptions getDefBotOptions() {
        DefaultBotOptions botOptions = ApiContext.getInstance(DefaultBotOptions.class);
        botOptions.setProxyHost("127.0.0.1");
        botOptions.setProxyPort(9150);
        botOptions.setProxyType(DefaultBotOptions.ProxyType.SOCKS5);
        return botOptions;
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        // Do any additional configuration here
        return builder.build();
    }
}
