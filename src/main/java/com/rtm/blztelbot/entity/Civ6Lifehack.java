package com.rtm.blztelbot.entity;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "CIV_LIFEHACK")
public class Civ6Lifehack {

    @Id
    @GeneratedValue
    @Column(name = "ID", updatable = false, nullable = false)
    private UUID id;

    @Version
    private Long version;

    @Column(name = "POSTED", nullable = false)
    private Boolean posted = false;

    @Column(name = "LIFEHACK", columnDefinition="TEXT", nullable = false)
    private String lifehack;

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

    public Boolean getPosted() {
        return posted;
    }

    public void setPosted(Boolean posted) {
        this.posted = posted;
    }

    public String getLifehack() {
        return lifehack;
    }

    public void setLifehack(String lifehack) {
        this.lifehack = lifehack;
    }
}
