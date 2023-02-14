package com.rtm.blztelbot.repository;

import com.rtm.blztelbot.entity.Civ6Lifehack;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface Civ6LifehackRepository extends JpaRepository<Civ6Lifehack, UUID> {

    Civ6Lifehack findFirstByPostedFalse();
}
