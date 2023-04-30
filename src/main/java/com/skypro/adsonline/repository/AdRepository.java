package com.skypro.adsonline.repository;

import com.skypro.adsonline.model.AdEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdRepository extends JpaRepository<AdEntity, Integer> {
}
