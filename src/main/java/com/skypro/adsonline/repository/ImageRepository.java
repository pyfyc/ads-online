package com.skypro.adsonline.repository;

import com.skypro.adsonline.model.ImageEntity;
import com.skypro.adsonline.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<ImageEntity, Integer> {
    ImageEntity findByUser(UserEntity user);
}
