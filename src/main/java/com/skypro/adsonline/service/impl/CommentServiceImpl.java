package com.skypro.adsonline.service.impl;

import com.skypro.adsonline.dto.Comment;
import com.skypro.adsonline.dto.ResponseWrapperComment;
import com.skypro.adsonline.exception.AdNotFoundException;
import com.skypro.adsonline.exception.CommentNotFoundException;
import com.skypro.adsonline.model.AdEntity;
import com.skypro.adsonline.model.CommentEntity;
import com.skypro.adsonline.repository.AdRepository;
import com.skypro.adsonline.repository.CommentRepository;
import com.skypro.adsonline.service.CommentService;
import com.skypro.adsonline.utils.CommentMapper;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.skypro.adsonline.constant.ErrorMessage.AD_NOT_FOUND_MSG;
import static com.skypro.adsonline.constant.ErrorMessage.COMMENT_NOT_FOUND_MSG;

@Service
public class CommentServiceImpl implements CommentService {

    private final AdRepository adRepository;
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    public CommentServiceImpl(AdRepository adRepository, CommentRepository commentRepository, CommentMapper commentMapper) {
        this.adRepository = adRepository;
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;
    }

    @Override
    public ResponseWrapperComment getComments(Integer id) {
        List<Comment> comments = commentRepository.findByAdId(id).stream()
                .map(comment -> commentMapper.mapToCommentDto(comment))
                .toList();
        return new ResponseWrapperComment(comments.size(), comments);
    }

    @Override
    public Comment addComment(Integer id, Comment commentDto) {
        AdEntity ad = adRepository.findById(id).orElseThrow(() -> new AdNotFoundException(AD_NOT_FOUND_MSG.formatted(id)));

        CommentEntity comment = new CommentEntity();
        comment.setAuthor(ad.getAuthor());
        comment.setAd(ad);
        comment.setCreatedAt(System.currentTimeMillis());
        comment.setText(commentDto.getText());

        commentRepository.save(comment);

        return commentMapper.mapToCommentDto(comment);
    }

    @Override
    public boolean deleteComment(Integer adId, Integer commentId) {
        CommentEntity comment = commentRepository.findById(commentId).orElseThrow(() -> new CommentNotFoundException(COMMENT_NOT_FOUND_MSG.formatted(commentId)));
        commentRepository.delete(comment);
        return true;
    }

    @Override
    public Comment updateComment(Integer adId, Integer commentId, Comment comment) {
        AdEntity ad = adRepository.findById(adId).
                orElseThrow(() -> new AdNotFoundException(AD_NOT_FOUND_MSG.formatted(adId)));
        CommentEntity foundComment = commentRepository.findById(commentId).
                orElseThrow(() -> new CommentNotFoundException(COMMENT_NOT_FOUND_MSG.formatted(commentId)));
        foundComment.setText(comment.getText());
        commentRepository.save(foundComment);
        return commentMapper.mapToCommentDto(foundComment);
    }
}
