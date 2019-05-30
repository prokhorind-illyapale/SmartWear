package ua.javaee.springreact.web.facade;

import ua.javaee.springreact.web.data.UserClothAttributeData;
import ua.javaee.springreact.web.data.UserData;

import java.util.List;

public interface UserClothAttributeFacade {

    UserClothAttributeData get(Long code);

    List<UserClothAttributeData> get(String userName, int pageNumber, int size);

    void remove(Long code);

    boolean save(UserClothAttributeData userClothAttributeData);

    boolean update(UserClothAttributeData userClothAttributeData, Long code);

    boolean isUserClothAttributes(UserData userData, Long code);
}
