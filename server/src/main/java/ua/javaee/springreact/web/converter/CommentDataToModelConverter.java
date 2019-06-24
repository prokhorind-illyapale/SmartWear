package ua.javaee.springreact.web.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.javaee.springreact.web.data.CommentData;
import ua.javaee.springreact.web.entity.Comment;
import ua.javaee.springreact.web.service.CommentService;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.stream.Collectors.*;

@Component
public class CommentDataToModelConverter implements AbstractConverter<Comment, CommentData> {
    @Autowired
    private CommentService commentService;
    @Autowired
    private LookDataToModelConverter lookDataToModelConverter;

    @Override
    public Comment convert(CommentData source) {
        Comment target = commentService.findCommentById(source.getCommentId());
        if (isNull(target)) {
           target = new Comment();
        }
        target.setCommentId(source.getCommentId());
        target.setLastUpdated(new Date());
        target.setLogin(source.getLogin());
        target.setLook(lookDataToModelConverter.convert(source.getLookData()));
        target.setMessage(source.getMessage());
        return target;
    }

    public List<Comment> convertAll(List<CommentData> source) {
        return source.stream().map(this::convert).collect(toList());
    }
}
