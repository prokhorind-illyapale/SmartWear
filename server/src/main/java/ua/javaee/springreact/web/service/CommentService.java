package ua.javaee.springreact.web.service;

import ua.javaee.springreact.web.entity.Comment;

import java.util.List;

/**
 * Created by kleba on 24.02.2019.
 */
public interface CommentService {
    List<Comment> findCommentsByLookCode(String code);
}
