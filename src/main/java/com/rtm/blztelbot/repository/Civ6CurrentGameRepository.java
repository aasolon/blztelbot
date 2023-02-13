package com.rtm.blztelbot.repository;

import com.rtm.blztelbot.entity.Civ6CurrentGame;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface Civ6CurrentGameRepository extends JpaRepository<Civ6CurrentGame, UUID> {

    Civ6CurrentGame findFirstByGameName(String gameName);
}
