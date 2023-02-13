package com.rtm.blztelbot.entity;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "CIV_CURRENT_GAME")
public class Civ6CurrentGame {

    @Id
    @GeneratedValue
    @Column(name = "ID", updatable = false, nullable = false)
    private UUID id;

    @Version
    private Long version;

    @Column(name = "GAME_NAME", length = 4000, nullable = false)
    private String gameName;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }
}
