package ua.javaee.springreact.web.service;

import ua.javaee.springreact.web.entity.Look;
import ua.javaee.springreact.web.entity.UserClothAttribute;

import java.util.List;

/**
 * Created by kleba on 24.02.2019.
 */
public interface LookService {

    Look findByCode(String code);

    List<Look> findAllUserLooks(String login);

    void removeDeletedUserClothAttributesFromLooks(List<UserClothAttribute> userClothAttributes);

    boolean isLookPublic(String code);

    boolean isUserHasLookNumber(String code);

    boolean isPrincipalLook(String code, String login);

    void deleteLookByCode(String code);
}
