package com.skypro.adsonline.repository;

import com.skypro.adsonline.model.AdEntity;
import com.skypro.adsonline.model.AdImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdImageRepository extends JpaRepository<AdImageEntity, Integer> {
    Optional<AdImageEntity> findByAd(AdEntity ad);
}
