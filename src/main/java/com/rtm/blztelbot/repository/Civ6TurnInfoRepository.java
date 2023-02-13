package com.rtm.blztelbot.repository;

import com.rtm.blztelbot.entity.Civ6TurnInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface Civ6TurnInfoRepository extends JpaRepository<Civ6TurnInfo, UUID> {

    List<Civ6TurnInfo> findAllByCreateDatetimeAfterOrCreateDatetimeEqualsOrderByCreateDatetime(Instant instant1, Instant instant2);

    Civ6TurnInfo findFirstByCreateDatetimeBeforeOrderByCreateDatetimeDesc(Instant instant1);
}
