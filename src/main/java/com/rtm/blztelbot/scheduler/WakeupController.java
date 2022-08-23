package com.rtm.blztelbot.scheduler;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WakeupController {
    private final WakeupService wakeupService;

    public WakeupController(WakeupService wakeupService) {
        this.wakeupService = wakeupService;
    }

    @RequestMapping("/wakeup")
    public void wakeUp() {
        wakeupService.sendWakeUpMessageToMe("wakeup1");
    }
}
