package com.rtm.blztelbot.api;

import com.rtm.blztelbot.AppConfig;
import com.rtm.blztelbot.api.oauth2.OAuth2FlowHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.Map;

@Service
public class ApiInvoker {

    @Autowired
    private AppConfig appConfig;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private OAuth2FlowHandler oAuth2FlowHandler;

    public <T> T getDataFromRelativePath(final String relativePath, final Map<String, String> params, Class<T> clazz)
            throws URISyntaxException, IOException {
        String token = oAuth2FlowHandler.getToken();

        UriComponentsBuilder uriBuilder = UriComponentsBuilder
                .fromUri(appConfig.getBaseUrl().toURI())
                .path(relativePath);

        params.forEach(uriBuilder::queryParam);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", String.format("Bearer %s", token));

        URI uri = uriBuilder.build().toUri();

        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        ResponseEntity<T> result = restTemplate.exchange(uri, HttpMethod.GET, entity, clazz);

        return result.getBody();
    }
}
