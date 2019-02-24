package ua.javaee.springreact.web.service;

import ua.javaee.springreact.web.entity.Look;

import java.util.List;

/**
 * Created by kleba on 24.02.2019.
 */
public interface LookService {

    Look findByCode(String code);

    List<Look> findAllUserLooks(String login);

    boolean isLookPublic(String code);

    boolean isUserHasLookNumber(String code);

    boolean isPrincipalLook(String code, String login);

    void deleteLookByCode(String code);

    List<Look> getLooksByLogin(String login);
}
