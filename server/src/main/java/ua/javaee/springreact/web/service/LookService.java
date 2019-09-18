package ua.javaee.springreact.web.service;

import org.springframework.web.multipart.MultipartFile;
import ua.javaee.springreact.web.data.LookData;
import ua.javaee.springreact.web.entity.Look;
import ua.javaee.springreact.web.entity.UserClothAttribute;

import java.util.List;

/**
 * Created by kleba on 24.02.2019.
 */
public interface LookService {

    Look findByCode(long code);

    List<Look> findAllUserLooks(String login);

    void removeDeletedUserClothAttributesFromLooks(List<UserClothAttribute> userClothAttributes);

    boolean isLookPublic(long code);

    boolean isUserHasLookNumber(long code);

    boolean isPrincipalLook(long code, String login);

    void deleteLookByCode(long code);

    void save(LookData lookData);

    void savePicture(MultipartFile picture, long code);

    Long getLastRow();

    boolean addLike(long code, String login);

    boolean removeLike(String login,long code);
}
