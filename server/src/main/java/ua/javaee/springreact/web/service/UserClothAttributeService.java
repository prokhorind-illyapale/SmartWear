package ua.javaee.springreact.web.service;

import ua.javaee.springreact.web.entity.UserClothAttribute;

public interface UserClothAttributeService {

    UserClothAttribute get(Long code);

    void remove(Long code);

    boolean save(UserClothAttribute userClothAttribute);

    long count();
}
