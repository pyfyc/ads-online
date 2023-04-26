package com.skypro.adsonline.service.impl;

import com.skypro.adsonline.dto.Comment;
import com.skypro.adsonline.service.CommentService;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    @Override
    public boolean getComments(Integer id) {
        return false;
    }

    @Override
    public boolean addComment(Integer id, Comment comment) {
        return false;
    }

    @Override
    public boolean deleteComment(Integer adId, Integer commentId) {
        return false;
    }

    @Override
    public boolean updateComment(Integer adId, Integer commentId, Comment comment) {
        return false;
    }
}
