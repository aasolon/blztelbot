package com.rtm.blztelbot.entity;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "CIV_TURN_INFO")
public class Civ6TurnInfo {

    @Id
    @GeneratedValue
    @Column(name = "ID", updatable = false, nullable = false)
    private UUID id;

    @Version
    private Long version;

//    @Generated(GenerationTime.INSERT)
//    @Column(name = "ROW_NUMBER", columnDefinition = "serial", updatable = false, nullable = false)
//    private Integer rowNumber;

    @Column(name = "GAME_NAME", length = 4000, nullable = false)
    private String gameName;

    @Column(name = "TURN_NUMBER", nullable = false)
    private Long turnNumber;

    @Column(name = "PLAYER_NAME", length = 4000, nullable = false)
    private String playerName;

    @CreationTimestamp
    @Column(name = "CREATE_DATETIME", nullable = false)
    private Instant createDatetime;

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

//    public Integer getRowNumber() {
//        return rowNumber;
//    }
//
//    public void setRowNumber(Integer rowNumber) {
//        this.rowNumber = rowNumber;
//    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public Long getTurnNumber() {
        return turnNumber;
    }

    public void setTurnNumber(Long turnNumber) {
        this.turnNumber = turnNumber;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public Instant getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Instant createDatetime) {
        this.createDatetime = createDatetime;
    }
}
