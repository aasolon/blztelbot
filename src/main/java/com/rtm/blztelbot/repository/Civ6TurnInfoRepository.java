package com.rtm.blztelbot.repository;

import com.rtm.blztelbot.entity.Civ6TurnInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface Civ6TurnInfoRepository extends JpaRepository<Civ6TurnInfo, UUID> {
}
