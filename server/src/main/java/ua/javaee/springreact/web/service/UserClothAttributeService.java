package ua.javaee.springreact.web.service;

import ua.javaee.springreact.web.entity.UserClothAttribute;

import java.util.List;

public interface UserClothAttributeService {

    UserClothAttribute get(Long code);
    void remove(Long code);
    boolean save(UserClothAttribute userClothAttribute);
    long count();

    long getLastRow();
    List<UserClothAttribute> get(String userName, int pageNumber, int size);
}
