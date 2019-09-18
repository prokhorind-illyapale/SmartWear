package ua.javaee.springreact.web.facade;

import org.springframework.web.multipart.MultipartFile;
import ua.javaee.springreact.web.data.LookData;
import ua.javaee.springreact.web.entity.Look;

import java.util.List;

/**
 * Created by kleba on 24.02.2019.
 */
public interface LookFacade {
    LookData findByCode(long code);

    List<LookData> findAllUserLooks(String login);

    boolean isLookPublic(long code);

    boolean isLookNumberExists(long code);

    boolean isPrincipalLook(long code, String login);

    Look findModelByCode(long code);

    void deleteLookByCode(long code);

    long saveLook(LookData look);

    void updateLook(LookData look);

    boolean addLike(String login,long code);

    boolean removeLike(String login,long code);

    void savePicture(MultipartFile multipartFile, long code);

}
