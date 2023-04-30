package com.skypro.adsonline.utils;

import com.skypro.adsonline.dto.Comment;
import com.skypro.adsonline.exception.AdNotFoundException;
import com.skypro.adsonline.exception.UserNotFoundException;
import com.skypro.adsonline.model.AdEntity;
import com.skypro.adsonline.model.CommentEntity;
import com.skypro.adsonline.model.UserEntity;
import com.skypro.adsonline.repository.AdRepository;
import com.skypro.adsonline.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class CommentMapper {

    private final UserRepository userRepository;
    private final AdRepository adRepository;

    public CommentMapper(UserRepository userRepository, AdRepository adRepository) {
        this.userRepository = userRepository;
        this.adRepository = adRepository;
    }

    /**
     * Dto -> entity mapping
     * @param adId advertising id
     * @param dto input dto class
     * @return entity class
     */
    public CommentEntity mapToCommentEntity(Integer adId, Comment dto) {
        UserEntity author = userRepository.findById(dto.getAuthor()).orElse(null);
        if (author == null) {
            throw new UserNotFoundException("Author not found");
        }
        AdEntity ad = adRepository.findById(adId).orElse(null);
        if (ad == null) {
            throw new AdNotFoundException("Ad not found");
        }
        CommentEntity entity = new CommentEntity();
        entity.setAuthor(author);
        entity.setAd(ad);
        entity.setCreatedAt(dto.getCreatedAt());
        entity.setText(dto.getText());
        return entity;
    }

    /**
     * Entity -> dto mapping
     * @param entity input entity class
     * @return dto class
     */
    public Comment mapToCommentDto(CommentEntity entity) {
        Comment dto = new Comment();
        dto.setAuthor(entity.getAuthor().getId());
        dto.setAuthorImage(entity.getAuthor().getImage());
        dto.setAuthorFirstName(entity.getAuthor().getFirstName());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setPk(entity.getId());
        dto.setText(entity.getText());
        return dto;
    }
}
