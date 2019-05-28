package ua.javaee.springreact.web.facade;

import ua.javaee.springreact.web.data.UserClothAttributeData;
import ua.javaee.springreact.web.data.UserData;

public interface UserClothAttributeFacade {

    UserClothAttributeData get(Long code);

    void remove(Long code);

    boolean save(UserClothAttributeData userClothAttributeData);

    boolean isUserClothAttributes(UserData userData, Long code);
}
