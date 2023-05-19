package com.skypro.adsonline.repository;

import com.skypro.adsonline.model.AdEntity;
import com.skypro.adsonline.model.ImageEntity;
import com.skypro.adsonline.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageRepository extends JpaRepository<ImageEntity, Integer> {
    Optional<ImageEntity> findByUser(UserEntity user);
    Optional<ImageEntity> findByAd(AdEntity ad);
}
