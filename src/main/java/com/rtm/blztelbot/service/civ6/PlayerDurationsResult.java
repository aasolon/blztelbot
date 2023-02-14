package com.rtm.blztelbot.service.civ6;

import java.time.Duration;
import java.util.Map;

public class PlayerDurationsResult {

    private Map<String, Duration> playerDurations;

    private long turnsCount;

    public Map<String, Duration> getPlayerDurations() {
        return playerDurations;
    }

    public void setPlayerDurations(Map<String, Duration> playerDurations) {
        this.playerDurations = playerDurations;
    }

    public long getTurnsCount() {
        return turnsCount;
    }

    public void setTurnsCount(long turnsCount) {
        this.turnsCount = turnsCount;
    }
}
