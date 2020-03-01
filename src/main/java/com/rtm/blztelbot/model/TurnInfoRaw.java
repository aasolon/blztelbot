package com.rtm.blztelbot.model;

import java.time.LocalDateTime;

public class TurnInfoRaw {
    private long id;
    private LocalDateTime endDate;

    public TurnInfoRaw(long id, LocalDateTime endDate) {
        this.id = id;
        this.endDate = endDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }
}
