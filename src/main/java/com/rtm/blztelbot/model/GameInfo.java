package com.rtm.blztelbot.model;

import java.util.List;

public class GameInfo {
    private String gameName;
    private List<PlayerInfo> players;

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public List<PlayerInfo> getPlayers() {
        return players;
    }

    public void setPlayers(List<PlayerInfo> players) {
        this.players = players;
    }
}
