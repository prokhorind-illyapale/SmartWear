package ua.javaee.springreact.web.converter;

import org.springframework.stereotype.Component;
import ua.javaee.springreact.web.data.CommentData;
import ua.javaee.springreact.web.form.lookforms.CommentForm;

/**
 * Created by kleba on 25.02.2019.
 */
@Component
public class CommentDataToFormConverter implements AbstractConverter<CommentForm, CommentData> {

    @Override
    public CommentForm convert(CommentData source) {
        CommentForm target = new CommentForm();
        target.setLogin(source.getLogin());
        target.setMessage(source.getMessage());
        target.setLastUpdated(source.getLastUpdated());
        return target;
    }
}
