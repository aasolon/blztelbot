package com.rtm.blztelbot.entity;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "CIV_PLAYER")
public class Civ6Player {

    @Id
    @GeneratedValue
    @Column(name = "ID", updatable = false, nullable = false)
    private UUID id;

    @Version
    private Long version;

    @Column(name = "CIV_NAME", length = 4000, nullable = false)
    private String civName;

    @Column(name = "TELEGRAM_NAME", length = 4000, nullable = false)
    private String telegramName;

    @Column(name = "TELEGRAM_ID", nullable = false)
    private Long telegramId;

    @Column(name = "TURN_ORDER")
    private Long turnOrder;

    @Column(name = "ACTIVE", nullable = false)
    private Boolean active = false;

    @Column(name = "GAME_URL", length = 4000)
    private String gameUrl;

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

    public String getCivName() {
        return civName;
    }

    public void setCivName(String civName) {
        this.civName = civName;
    }

    public String getTelegramName() {
        return telegramName;
    }

    public void setTelegramName(String telegramName) {
        this.telegramName = telegramName;
    }

    public Long getTelegramId() {
        return telegramId;
    }

    public void setTelegramId(Long telegramId) {
        this.telegramId = telegramId;
    }

    public Long getTurnOrder() {
        return turnOrder;
    }

    public void setTurnOrder(Long turnOrder) {
        this.turnOrder = turnOrder;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getGameUrl() {
        return gameUrl;
    }

    public void setGameUrl(String gameUrl) {
        this.gameUrl = gameUrl;
    }
}
