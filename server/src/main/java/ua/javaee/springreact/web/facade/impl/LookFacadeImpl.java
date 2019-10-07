package ua.javaee.springreact.web.facade.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ua.javaee.springreact.web.converter.AbstractConverter;
import ua.javaee.springreact.web.data.CommentData;
import ua.javaee.springreact.web.data.LookData;
import ua.javaee.springreact.web.entity.Comment;
import ua.javaee.springreact.web.entity.Look;
import ua.javaee.springreact.web.entity.UserClothAttribute;
import ua.javaee.springreact.web.facade.LookFacade;
import ua.javaee.springreact.web.service.CommentService;
import ua.javaee.springreact.web.service.LookService;
import ua.javaee.springreact.web.service.UserClothAttributeService;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Created by kleba on 24.02.2019.
 */
@Component
public class LookFacadeImpl implements LookFacade {
    @Autowired
    private LookService lookService;
    @Autowired
    private UserClothAttributeService userClothAttributeService;
    @Autowired
    @Qualifier("lookModelToLookDataConverter")
    private AbstractConverter lookModelToLookDataConverter;
    @Autowired
    @Qualifier("commentModelToDataConverter")
    private AbstractConverter commentModelToDataConverter;
    @Autowired
    private CommentService commentService;

    @Override
    public LookData findByCode(long code) {
        Look look = lookService.findByCode(code);
        LookData lookData = (LookData) lookModelToLookDataConverter.convert(look);
        lookData.setComments(getCommentDatas(look));
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
    public boolean isLookPublic(long code) {
        return lookService.isLookPublic(code);
    }

    @Override
    public boolean isLookNumberExists(long code) {
        return lookService.isUserHasLookNumber(code);
    }

    @Override
    public boolean isPrincipalLook(long code, String login) {
        return lookService.isPrincipalLook(code, login);
    }

    @Override
    public Look findModelByCode(long code) {
        return lookService.findByCode(code);
    }

    @Override
    public void deleteLookByCode(long code) {
        lookService.deleteLookByCode(code);
    }

    @Override
    public long saveLook(LookData look) {
        long code = lookService.getLastRow() + 1l;
        look.setCode(code);
        lookService.save(look);
        return code;
    }

    @Override
    public void updateLook(LookData lookData) {
        lookData.setCode(lookData.getCode());
        lookService.save(lookData);
    }

    @Override
    public boolean addLike(String login, long code) {
        return lookService.addLike(code, login);
    }

    @Override
    public boolean removeLike(String login, long code) {
        return lookService.removeLike(login, code);
    }

    @Override
    public void savePicture(MultipartFile multipartFile, long code) {
        lookService.savePicture(multipartFile, code);
    }

    @Override
    public void addClothToLook(long lookCode, long clothCode) {
        Look look = lookService.findByCode(lookCode);
        UserClothAttribute attribute = userClothAttributeService.get(clothCode);
        look.getUserClothAttributes().add(attribute);
        lookService.save(look);
    }

    @Override
    public void removeClothFromLook(long lookCode, long clothCode) {
        Look look = lookService.findByCode(lookCode);
        UserClothAttribute attribute = userClothAttributeService.get(clothCode);
        look.getUserClothAttributes().remove(attribute);
        lookService.save(look);
    }

    @Override
    public List<LookData> findMostPopularUserLooks(String login, int minTemperature, int maxTemperature, int limit, String sex) {
        List<Look> looks = lookService.findMostPopularUserLooks(login, minTemperature, maxTemperature, limit, sex);
        List<LookData> lookDatas = looks.stream().map(l -> (LookData) lookModelToLookDataConverter.convert(l)).collect(toList());
        return lookDatas;
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
