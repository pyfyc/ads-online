package com.skypro.adsonline.service.impl;

import com.skypro.adsonline.dto.Comment;
import com.skypro.adsonline.dto.ResponseWrapperComment;
import com.skypro.adsonline.dto.User;
import com.skypro.adsonline.exception.AdNotFoundException;
import com.skypro.adsonline.model.AdEntity;
import com.skypro.adsonline.model.CommentEntity;
import com.skypro.adsonline.model.UserEntity;
import com.skypro.adsonline.repository.AdRepository;
import com.skypro.adsonline.repository.CommentRepository;
import com.skypro.adsonline.repository.UserRepository;
import com.skypro.adsonline.service.CommentService;
import com.skypro.adsonline.utils.CommentMapper;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    AdRepository adRepository;
    CommentRepository commentRepository;
    CommentMapper commentMapper;
    UserRepository userRepository;

    @Override
    public ResponseWrapperComment getComments(Integer id) {
        List<Comment> comments = commentRepository.findByAdId(id).stream()
                .map(comment -> commentMapper.mapToCommentDto(comment))
                .toList();
        return new ResponseWrapperComment(comments.size(), comments);
    }

    @Override
    public Comment addComment(Integer id, Comment comment) {
        CommentEntity newComment = commentMapper.mapToCommentEntity(id, comment);
        commentRepository.save(newComment);
        return commentMapper.mapToCommentDto(newComment);
    }

    @Override
    public boolean deleteComment(Integer adId, Integer commentId) {
        AdEntity ad = adRepository.findById(adId).
                orElseThrow(() -> new AdNotFoundException("Объявление не найдено".formatted(adId)));
        CommentEntity comment = commentRepository.findById(commentId).
                orElseThrow(() -> new NotFoundException("Комментарий не найден".formatted(commentId)));
        commentRepository.deleteById(commentId);
        return true;
    }

    @Override
    public Comment updateComment(Integer adId, Integer commentId, Comment comment) {
        AdEntity ad = adRepository.findById(adId).
                orElseThrow(() -> new AdNotFoundException("Объявление не найдено".formatted(adId)));
        CommentEntity foundComment = commentRepository.findById(commentId).
                orElseThrow(() -> new NotFoundException("Комментарий не найден".formatted(commentId)));
        foundComment.setText(comment.getText());
        commentRepository.save(foundComment);
        return commentMapper.mapToCommentDto(foundComment);
    }
}
