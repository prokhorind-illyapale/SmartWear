package ua.javaee.springreact.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.javaee.springreact.web.data.CommentData;
import ua.javaee.springreact.web.entity.Comment;
import ua.javaee.springreact.web.entity.Look;
import ua.javaee.springreact.web.repository.CommentRepository;
import ua.javaee.springreact.web.repository.LookRepository;
import ua.javaee.springreact.web.service.CommentService;
import ua.javaee.springreact.web.service.LookService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static java.util.Objects.*;

/**
 * Created by kleba on 24.02.2019.
 */
@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private LookRepository lookRepository;
    @Autowired
    private LookService lookService;

    @Override
    public List<Comment> findCommentsByLookCode(long code) {
        List<Comment> comments = new ArrayList<>();
        comments.addAll(commentRepository.findCommentsByLookCode(code));
        return comments;
    }

    @Override
    public Comment findCommentById(Long id) {
        return commentRepository.findById(id).get();
    }

    @Override
    public void save(Comment comment) {
        commentRepository.save(comment);
    }

    @Override
    public long addComment(String login, String message, long lookCode) {
        Look look = lookService.findByCode(lookCode);
        if (isNull(look)) {
            return 0l;
        }
        Comment comment = createMessage(login, message, look);
        look.getComments().add(comment);
        Comment savedComment = commentRepository.saveAndFlush(comment);
        lookRepository.save(look);
        return savedComment.getCommentId();
    }

    private Comment createMessage(String login, String message, Look look) {
        Comment comment = new Comment();
        comment.setMessage(message);
        comment.setLastUpdated(new Date());
        comment.setLogin(login);
        comment.setLook(look);
        return comment;
    }


    @Override
    public void removeComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }
}
