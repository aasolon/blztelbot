package com.rtm.blztelbot.entity;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;
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

    @Generated(GenerationTime.INSERT)
    @Column(name = "ROW_NUMBER", columnDefinition = "serial", updatable = false, nullable = false)
    private Integer rowNumber;

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

    public Integer getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(Integer rowNumber) {
        this.rowNumber = rowNumber;
    }
}
