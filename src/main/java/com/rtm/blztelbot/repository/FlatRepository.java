package com.rtm.blztelbot.repository;

import com.rtm.blztelbot.entity.FlatEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FlatRepository extends JpaRepository<FlatEntity, UUID> {

    FlatEntity findByIdFromSite(Long idFromSite);
}
