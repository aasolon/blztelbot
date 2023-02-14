package com.rtm.blztelbot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rtm.blztelbot.model.Civ6Webhook;
import com.rtm.blztelbot.service.FlatService;
import com.rtm.blztelbot.service.civ6.Civ6Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class MainController {

    private static final Logger log = LoggerFactory.getLogger(MainController.class);

    private final ObjectMapper objectMapper;
    private final FlatService flatService;
    private final Civ6Service civ6Service;

    public MainController(ObjectMapper objectMapper, FlatService flatService, Civ6Service civ6Service) {
        this.objectMapper = objectMapper;
        this.flatService = flatService;
        this.civ6Service = civ6Service;
    }

    @GetMapping("/refresh-flats")
    public void save() throws IOException {
        flatService.refreshFlats();
    }

    @RequestMapping("/civ6")
    public void civ6(@RequestBody Civ6Webhook webhook) throws IOException {
        log.debug("/civ6 received webhook = " + objectMapper.writeValueAsString(webhook));
        civ6Service.processCiv6Webhook(webhook);
    }
}
