package com.rtm.blztelbot.repository;

import com.rtm.blztelbot.entity.FlatStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FlatStatusRepository extends JpaRepository<FlatStatusEntity, UUID> {

    FlatStatusEntity findByError(String error);
}
