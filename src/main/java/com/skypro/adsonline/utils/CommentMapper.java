package com.skypro.adsonline.utils;

import com.skypro.adsonline.dto.Comment;
import com.skypro.adsonline.model.CommentEntity;
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
