package ua.javaee.springreact.web.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ua.javaee.springreact.web.data.CommentData;
import ua.javaee.springreact.web.data.LookData;
import ua.javaee.springreact.web.entity.Comment;

/**
 * Created by kleba on 24.02.2019.
 */
@Component
public class CommentModelToDataConverter implements AbstractConverter<CommentData, Comment> {
    @Autowired
    @Qualifier("lookModelToLookDataConverter")
    private AbstractConverter lookModelToLookDataConverter;

    @Override
    public CommentData convert(Comment source) {
        CommentData target = new CommentData();
        target.setLogin(source.getLogin());
        target.setMessage(source.getMessage());
        target.setCommentId(source.getCommentId());
        target.setLastUpdated(source.getLastUpdated());
        target.setLookData((LookData) lookModelToLookDataConverter.convert(source.getLook()));
        return target;
    }
}
