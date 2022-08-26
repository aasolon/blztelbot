package com.rtm.blztelbot.entity;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "FLAT")
public class FlatEntity {

    @Id
    @GeneratedValue
    @Column(name = "ID", updatable = false, nullable = false)
    private UUID id;

    @Version
    private Long version;

    @CreationTimestamp
    @Column(name = "CREATE_DATETIME", nullable = false)
    private Instant createDatetime;

    @Column(name = "ID_FROM_SITE", nullable = false)
    private Long idFromSite;

    @Column(name = "URL", length = 4000, nullable = false)
    private String url;

    @Column(name = "CORPUS", length = 255)
    private String corpus;

    @Column(name = "FLOOR")
    private Long floor;

    @Column(name = "FLOOR_MAX")
    private Long floorMax;

    @Column(name = "ROOMS_NUMBER")
    private Long roomsNumber;

    @Column(name = "AREA", precision = 20, scale = 2)
    private BigDecimal area;

    @Column(name = "CHECK_IN", length = 255)
    private String checkIn;

    @Column(name = "ROOM_ON_THE_FLOOR")
    private Long roomOnTheFloor;

    @Column(name = "SECTION")
    private Long section;

    @Column(name = "FLAT_TYPE", length = 255)
    private String flatType;

    @OneToMany(mappedBy = "flat", cascade = CascadeType.ALL)
    private Set<FlatStatusEntity> flatStatuses = new HashSet<>();

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

    public Instant getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Instant createDatetime) {
        this.createDatetime = createDatetime;
    }

    public Long getIdFromSite() {
        return idFromSite;
    }

    public void setIdFromSite(Long idFromSite) {
        this.idFromSite = idFromSite;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCorpus() {
        return corpus;
    }

    public void setCorpus(String corpus) {
        this.corpus = corpus;
    }

    public Long getFloor() {
        return floor;
    }

    public void setFloor(Long floor) {
        this.floor = floor;
    }

    public Long getFloorMax() {
        return floorMax;
    }

    public void setFloorMax(Long floorMax) {
        this.floorMax = floorMax;
    }

    public Long getRoomsNumber() {
        return roomsNumber;
    }

    public void setRoomsNumber(Long roomsNumber) {
        this.roomsNumber = roomsNumber;
    }

    public BigDecimal getArea() {
        return area;
    }

    public void setArea(BigDecimal area) {
        this.area = area;
    }

    public String getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(String checkIn) {
        this.checkIn = checkIn;
    }

    public Long getRoomOnTheFloor() {
        return roomOnTheFloor;
    }

    public void setRoomOnTheFloor(Long roomOnTheFloor) {
        this.roomOnTheFloor = roomOnTheFloor;
    }

    public Long getSection() {
        return section;
    }

    public void setSection(Long section) {
        this.section = section;
    }

    public String getFlatType() {
        return flatType;
    }

    public void setFlatType(String flatType) {
        this.flatType = flatType;
    }

    public Set<FlatStatusEntity> getFlatStatuses() {
        return flatStatuses;
    }
}
