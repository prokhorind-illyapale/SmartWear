package ua.javaee.springreact.web.facade.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ua.javaee.springreact.web.converter.AbstractConverter;
import ua.javaee.springreact.web.data.CommentData;
import ua.javaee.springreact.web.data.LookData;
import ua.javaee.springreact.web.entity.Comment;
import ua.javaee.springreact.web.entity.Look;
import ua.javaee.springreact.web.facade.LookFacade;
import ua.javaee.springreact.web.service.LookService;
import ua.javaee.springreact.web.service.impl.CommentService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kleba on 24.02.2019.
 */
@Component
public class LookFacadeImpl implements LookFacade {
    @Autowired
    private LookService lookService;
    @Autowired
    @Qualifier("lookModelToLookDataConverter")
    private AbstractConverter lookModelToLookDataConverter;
    @Autowired
    @Qualifier("commentModelToDataConverter")
    private AbstractConverter commentModelToDataConverter;
    @Autowired
    private CommentService commentService;

    @Override
    public LookData findByCode(String code) {
        Look look = lookService.findByCode(code);
        LookData lookData = (LookData) lookModelToLookDataConverter.convert(look);
        return lookData;
    }

    @Override
    public List<LookData> findAllUserLooks(String login) {
        List<Look> looks = lookService.findAllUserLooks(login);
        List<LookData> lookDatas = new ArrayList<>();
        for (Look look : looks) {
            LookData lookData = (LookData) lookModelToLookDataConverter.convert(look);
            lookData.setComments(getCommentDatas(look));
            lookDatas.add(lookData);
        }
        return lookDatas;
    }

    @Override
    public boolean isLookPublic(String code) {
        return lookService.isLookPublic(code);
    }

    @Override
    public boolean isLookNumberExists(String code) {
        return lookService.isUserHasLookNumber(code);
    }

    @Override
    public boolean isPrincipalLook(String code, String login) {
        return lookService.isPrincipalLook(code, login);
    }

    @Override
    public Look findModelByCode(String code) {
        return lookService.findByCode(code);
    }

    @Override
    public void deleteLookByCode(String code) {
        lookService.deleteLookByCode(code);
    }

    private List<CommentData> getCommentDatas(Look source) {
        List<CommentData> comments = new ArrayList<>();
        for (Comment c : commentService.findCommentsByLookCode(source.getCode())) {
            CommentData comment = (CommentData) commentModelToDataConverter.convert(c);
            comments.add(comment);
        }
        return comments;
    }
}
