package com.skypro.adsonline.repository;

import com.skypro.adsonline.model.AdEntity;
import com.skypro.adsonline.model.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Integer> {
    List<CommentEntity> findByAd(AdEntity ad);
}
