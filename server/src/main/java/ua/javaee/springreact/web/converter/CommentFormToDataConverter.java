package ua.javaee.springreact.web.converter;

import org.springframework.stereotype.Component;
import ua.javaee.springreact.web.data.CommentData;
import ua.javaee.springreact.web.form.lookforms.CommentForm;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class CommentFormToDataConverter implements AbstractConverter<CommentData, CommentForm> {
    @Override
    public CommentData convert(CommentForm source) {
        CommentData target = new CommentData();
        target.setLastUpdated(source.getLastUpdated());
        target.setLogin(source.getLogin());
        target.setMessage(source.getMessage());
        target.setCommentId(source.getCommentId());
        return target;
    }

    public List<CommentData> convertAll(List<CommentForm> source){
        return source.stream().map(this::convert).collect(toList());
    }
}
