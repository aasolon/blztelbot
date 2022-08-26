package com.rtm.blztelbot.controller;

import com.rtm.blztelbot.service.FlatService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class MainController {

    private final FlatService flatService;

    public MainController(FlatService flatService) {
        this.flatService = flatService;
    }

    @GetMapping("/refresh-flats")
    public void save() throws IOException {
        flatService.refreshFlats();
    }
}
