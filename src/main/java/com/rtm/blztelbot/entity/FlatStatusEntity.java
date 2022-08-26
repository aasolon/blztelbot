package com.rtm.blztelbot.entity;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "FLAT_STATUS")
public class FlatStatusEntity {

    @Id
    @GeneratedValue
    @Column(name = "ID", updatable = false, nullable = false)
    private UUID id;

    @Version
    private Long version;

    @ManyToOne
    @JoinColumn(name = "FLAT_ID", nullable = false)
    private FlatEntity flat;

    @CreationTimestamp
    @Column(name = "CREATE_DATETIME", nullable = false)
    private Instant createDatetime;

    @Column(name = "PRICE")
    private Long price;

    @Column(name = "RESERVE", nullable = false)
    private Boolean reserve = false;

    @Column(name = "ACTIVE", nullable = false)
    private Boolean active = false;

    @Column(name = "ERROR", length = 4000)
    private String error;

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

    public FlatEntity getFlat() {
        return flat;
    }

    public void setFlat(FlatEntity flat) {
        this.flat = flat;
    }

    public Instant getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Instant createDatetime) {
        this.createDatetime = createDatetime;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Boolean getReserve() {
        return reserve;
    }

    public void setReserve(Boolean reserve) {
        this.reserve = reserve;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FlatStatusEntity that = (FlatStatusEntity) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
