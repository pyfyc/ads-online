package com.skypro.adsonline.service;

import com.skypro.adsonline.dto.Comment;

public interface CommentService {

    boolean getComments(Integer id);

    boolean addComment(Integer id, Comment comment);

    boolean deleteComment(Integer adId, Integer commentId);

    boolean updateComment(Integer adId, Integer commentId, Comment comment);
}
