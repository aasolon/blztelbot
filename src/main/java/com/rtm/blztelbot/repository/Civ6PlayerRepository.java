package com.rtm.blztelbot.repository;

import com.rtm.blztelbot.entity.Civ6Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface Civ6PlayerRepository extends JpaRepository<Civ6Player, UUID> {

    Civ6Player findByCivName(String civName);
}
