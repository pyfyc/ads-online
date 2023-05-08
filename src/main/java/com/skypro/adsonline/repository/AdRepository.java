package com.skypro.adsonline.repository;

import com.skypro.adsonline.model.AdEntity;
import com.skypro.adsonline.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdRepository extends JpaRepository<AdEntity, Integer> {

    List<AdEntity> findByAuthor(UserEntity author);

    List<AdEntity> findByTitleContainingIgnoreCase(String title);
}
