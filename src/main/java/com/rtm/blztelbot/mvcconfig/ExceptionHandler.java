package com.rtm.blztelbot.mvcconfig;

import com.rtm.blztelbot.service.BlzTelBotService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ExceptionHandler {

    @Autowired
    private BlzTelBotService blzTelBotService;

    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public void handleCommonException(Exception ex) {
        blzTelBotService.sendMessageToMe(StringUtils.abbreviate(ExceptionUtils.getStackTrace(ex), 4000));
        ex.printStackTrace();
    }

}
