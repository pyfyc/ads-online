package com.skypro.adsonline.repository;

import com.skypro.adsonline.model.AvatarEntity;
import com.skypro.adsonline.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AvatarRepository extends JpaRepository<AvatarEntity, Integer> {
    Optional<AvatarEntity> findByUser(UserEntity user);
}
