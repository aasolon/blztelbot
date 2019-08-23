package com.rtm.blztelbot;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.net.URL;

@ConfigurationProperties(prefix = "main")
public class AppConfig {

    private URL baseUrl;
    private URL tokenUrl;

    public URL getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(URL baseUrl) {
        this.baseUrl = baseUrl;
    }

    public URL getTokenUrl() {
        return tokenUrl;
    }

    public void setTokenUrl(URL tokenUrl) {
        this.tokenUrl = tokenUrl;
    }
}
