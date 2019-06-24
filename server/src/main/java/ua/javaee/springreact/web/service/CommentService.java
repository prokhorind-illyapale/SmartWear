package ua.javaee.springreact.web.service;

import ua.javaee.springreact.web.data.CommentData;
import ua.javaee.springreact.web.entity.Comment;

import java.util.List;

/**
 * Created by kleba on 24.02.2019.
 */
public interface CommentService {
    List<Comment> findCommentsByLookCode(long code);

    Comment findCommentById(Long id);

    void save(Comment comment);

    long addComment(String login, String message, long lookCode);

    void removeComment(Long commentId);
}
