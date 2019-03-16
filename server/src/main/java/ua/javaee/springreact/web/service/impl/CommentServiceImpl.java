package ua.javaee.springreact.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.javaee.springreact.web.entity.Comment;
import ua.javaee.springreact.web.repository.CommentRepository;
import ua.javaee.springreact.web.service.CommentService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kleba on 24.02.2019.
 */
@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Override
    public List<Comment> findCommentsByLookCode(String code) {
        List<Comment> comments = new ArrayList<>();
        comments.addAll(commentRepository.findCommentsByLookCode(code));
        return comments;
    }
}
