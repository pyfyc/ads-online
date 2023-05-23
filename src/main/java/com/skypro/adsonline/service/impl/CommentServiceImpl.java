package com.skypro.adsonline.service.impl;

import com.skypro.adsonline.dto.Comment;
import com.skypro.adsonline.dto.ResponseWrapperComment;
import com.skypro.adsonline.dto.Role;
import com.skypro.adsonline.exception.AdNotFoundException;
import com.skypro.adsonline.exception.CommentNotFoundException;
import com.skypro.adsonline.model.AdEntity;
import com.skypro.adsonline.model.CommentEntity;
import com.skypro.adsonline.model.UserEntity;
import com.skypro.adsonline.repository.AdRepository;
import com.skypro.adsonline.repository.CommentRepository;
import com.skypro.adsonline.service.CommentService;
import com.skypro.adsonline.service.UserService;
import com.skypro.adsonline.utils.CommentMapper;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static com.skypro.adsonline.constant.ErrorMessage.*;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {

    private final AdRepository adRepository;
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final UserService userService;

    public CommentServiceImpl(AdRepository adRepository, CommentRepository commentRepository, CommentMapper commentMapper, UserService userService) {
        this.adRepository = adRepository;
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;
        this.userService = userService;
    }

    @Override
    public ResponseWrapperComment getComments(Integer id, UserDetails currentUser) {
        List<Comment> comments = commentRepository.findByAdId(id).stream()
                .map(comment -> commentMapper.mapToCommentDto(comment))
                .toList();
        return new ResponseWrapperComment(comments.size(), comments);
    }

    @Override
    public Comment addComment(Integer id, Comment commentDto, UserDetails currentUser) {
        AdEntity ad = adRepository.findById(id).orElseThrow(() -> new AdNotFoundException(AD_NOT_FOUND_MSG.formatted(id)));
        UserEntity author = userService.checkUserByUsername(currentUser.getUsername());

        CommentEntity comment = new CommentEntity();
        comment.setAuthor(author);
        comment.setAd(ad);
        comment.setCreatedAt(System.currentTimeMillis());
        comment.setText(commentDto.getText());

        commentRepository.save(comment);

        return commentMapper.mapToCommentDto(comment);
    }

    @Override
    public boolean deleteComment(Integer adId, Integer commentId, UserDetails currentUser) {
        checkPermission(commentId, currentUser);
        CommentEntity comment = commentRepository.findById(commentId).orElseThrow(() -> new CommentNotFoundException(COMMENT_NOT_FOUND_MSG.formatted(commentId)));
        commentRepository.delete(comment);
        return true;
    }

    @Override
    public Comment updateComment(Integer adId, Integer commentId, Comment comment, UserDetails currentUser) {
        checkPermission(commentId, currentUser);
        AdEntity ad = adRepository.findById(adId).
                orElseThrow(() -> new AdNotFoundException(AD_NOT_FOUND_MSG.formatted(adId)));
        CommentEntity foundComment = commentRepository.findById(commentId).
                orElseThrow(() -> new CommentNotFoundException(COMMENT_NOT_FOUND_MSG.formatted(commentId)));
        foundComment.setText(comment.getText());
        commentRepository.save(foundComment);
        return commentMapper.mapToCommentDto(foundComment);
    }

    /**
     * Checks if the author of the comment is the same as logged-in user.
     * If it is not and the user is not an ADMIN then throw an exception.
     * @param commentId comment id
     * @param currentUser logged-in user
     */
    private void checkPermission(Integer commentId, UserDetails currentUser) {
        String authorUsername = commentRepository
                .findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException(COMMENT_NOT_FOUND_MSG.formatted(commentId)))
                .getAuthor()
                .getUsername();
        if (!authorUsername.equals(currentUser.getUsername())
                && !currentUser.getAuthorities().contains(new SimpleGrantedAuthority(Role.ADMIN.name()))) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, ACCESS_DENIED_MSG.formatted(currentUser.getUsername()));
        }
    }
}
