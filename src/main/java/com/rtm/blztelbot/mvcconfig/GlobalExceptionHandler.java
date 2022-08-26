package com.rtm.blztelbot.mvcconfig;

import com.rtm.blztelbot.service.BlzTelBotService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private final BlzTelBotService blzTelBotService;

    public GlobalExceptionHandler(BlzTelBotService blzTelBotService) {
        this.blzTelBotService = blzTelBotService;
    }

    @ExceptionHandler(Exception.class)
    public void handleCommonException(Exception ex) {
        blzTelBotService.sendMessageToMe(StringUtils.abbreviate(ExceptionUtils.getStackTrace(ex), 4000));
        ex.printStackTrace();
    }

}
