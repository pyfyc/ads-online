package com.skypro.adsonline.service;

import com.skypro.adsonline.dto.Comment;
import com.skypro.adsonline.dto.ResponseWrapperComment;

public interface CommentService {

    ResponseWrapperComment getComments(Integer id);

    Comment addComment(Integer id, Comment comment);


    void deleteComment(Integer adId, Integer commentId);

    Comment updateComment(Integer adId, Integer commentId, Comment comment);
}
