package com.rtm.blztelbot;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

import javax.servlet.http.HttpServletRequest;

@Configuration
public class AppConfig {

    //    @Bean
//    public DefaultBotOptions getDefBotOptions() {
//        DefaultBotOptions botOptions = ApiContext.getInstance(DefaultBotOptions.class);
//        botOptions.setProxyHost("127.0.0.1");
//        botOptions.setProxyPort(9150);
//        botOptions.setProxyType(DefaultBotOptions.ProxyType.SOCKS5);
//        return botOptions;
//    }

    @Bean
    public RestTemplate herokuRestTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    public RestTemplate pikRestTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    public CommonsRequestLoggingFilter logFilter() {
        CommonsRequestLoggingFilter filter = new CommonsRequestLoggingFilter() {
            @Override
            protected boolean shouldLog(HttpServletRequest request) {
                if ("/wakeup".equals(request.getRequestURI())) {
                    return false;
                }
                return super.shouldLog(request);
            }
        };
        filter.setIncludeQueryString(true);
        filter.setIncludePayload(true);
        filter.setMaxPayloadLength(10000);
        filter.setIncludeHeaders(true);
        filter.setIncludeClientInfo(true);
        return filter;
    }
}
