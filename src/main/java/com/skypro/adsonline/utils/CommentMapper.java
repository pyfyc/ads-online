package com.skypro.adsonline.utils;


import com.skypro.adsonline.dto.Comment;
import com.skypro.adsonline.model.CommentEntity;
import org.springframework.stereotype.Service;

@Service
public class CommentMapper {
    public Comment mapToCommentDto(CommentEntity entity) {
        Comment dto = new Comment();
        dto.setAuthor(entity.getAuthor());
        dto.setAuthorImage(entity.getAuthorImage());
        dto.setAuthorFirstName(entity.getAuthorFirstName());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setPk(entity.getPk());
        dto.setText(entity.getText());
        return dto;
    }

    public CommentEntity mapToCommentEntity(Comment dto) {
        CommentEntity entity = new CommentEntity();
        entity.setAuthor(dto.getAuthor());
        entity.setAuthorImage(dto.getAuthorImage());
        entity.setAuthorFirstName(dto.getAuthorFirstName());
        entity.setCreatedAt(dto.getCreatedAt());
        entity.setPk(dto.getPk());
        entity.setText(dto.getText());
        return entity;

    }
}
