package ua.javaee.springreact.web.facade;

import ua.javaee.springreact.web.data.LookData;

import java.util.List;

/**
 * Created by kleba on 24.02.2019.
 */
public interface LookFacade {
    LookData findByCode(String code);

    List<LookData> findAllUserLooks(String login);

    boolean isLookPublic(String code);

    boolean isLookNumberExists(String code);

    boolean isPrincipalLook(String code, String login);
}
