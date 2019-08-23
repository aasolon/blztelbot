package com.rtm.blztelbot.api.oauth2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rtm.blztelbot.AppConfig;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.net.www.protocol.http.Handler;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLStreamHandler;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Base64;

/**
 * A service for handling the oAuth2 flow, giving us an access token every time we want to interact with an API.
 */
@Service
public class OAuth2FlowHandler {

    @Autowired
    private AppConfig appConfig;

    @Autowired
    private ObjectMapper objectMapper;

    // To allow testing of the URL/Connection
    private URLStreamHandler urlStreamHandler = new Handler();

    private String token = null;
    private Instant tokenExpiry = null; // Instant when the token will expire

    private final Object tokenLock = new Object();

    /**
     * Attempts to re-use an existing token if one exists and is valid, otherwise it fetches one based on our client id
     * and client secret. This can be called repeatedly, as the token is stored in memory, and a call is only made if
     * the token is known to have expired.
     *
     * @return The Authorization Token used in future API requests.
     * @throws IOException if the downstream service encounters any issues
     */
    public String getToken() throws IOException {
        if (isTokenInvalid()) {
            String credentials = String.format("%s:%s", System.getenv("CLIENT_ID"), System.getenv("CLIENT_SECRET"));
            String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes(StandardCharsets.UTF_8));

            // ------------------------------------------------- Allows testing/mocking of the URL connection object
            HttpURLConnection con = null;

            try{
                URL url = new URL(appConfig.getTokenUrl(), "", urlStreamHandler);
                con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("POST");
                con.setRequestProperty("Authorization", String.format("Basic %s", encodedCredentials));
                con.setDoOutput(true);
                con.getOutputStream().write("grant_type=client_credentials".getBytes(StandardCharsets.UTF_8));

                String response = IOUtils.toString(con.getInputStream(), StandardCharsets.UTF_8);

                // Reads the JSON response and converts it to TokenResponse class or throws an exception
                TokenResponse tokenResponse = objectMapper.readValue(response, TokenResponse.class);
                synchronized (tokenLock) {
                    tokenExpiry = Instant.now().plusSeconds(tokenResponse.getExpires_in());
                    token = tokenResponse.getAccess_token();
                }
            } finally {
                if (con != null) {
                    con.disconnect();
                }
            }
        }
        synchronized (tokenLock){
            return token;
        }
    }

    /**
     * Checks to see if the known token is both valid and not expired.
     *
     * @return True if the token is eligible for future API requests immediately. False otherwise.
     */
    private boolean isTokenInvalid(){
        synchronized (tokenLock) {
            if (token == null) {
                return true;
            }
            if (tokenExpiry == null) {
                return true;
            }
            return Instant.now().isAfter(tokenExpiry);
        }
    }
}
