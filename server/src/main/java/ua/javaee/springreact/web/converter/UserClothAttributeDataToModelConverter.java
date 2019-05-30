package ua.javaee.springreact.web.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.javaee.springreact.web.data.ClothData;
import ua.javaee.springreact.web.data.UserClothAttributeData;
import ua.javaee.springreact.web.entity.UserClothAttribute;
import ua.javaee.springreact.web.service.ClothService;
import ua.javaee.springreact.web.service.UserClothAttributeService;
import ua.javaee.springreact.web.service.UserService;

import java.security.Principal;

import static org.springframework.security.core.context.SecurityContextHolder.getContext;

@Component
public class UserClothAttributeDataToModelConverter implements AbstractConverter<UserClothAttribute, UserClothAttributeData> {

    @Autowired
    private UserService userService;
    @Autowired
    private ClothService clothService;
    @Autowired
    private UserClothAttributeService userClothAttributeService;

    private Principal principal;

    @Override
    public UserClothAttribute convert(UserClothAttributeData source) {
        principal = getContext().getAuthentication();
        ClothData clothData = source.getCloth();
        UserClothAttribute target = new UserClothAttribute();
        target.setColor(source.getColor());
        target.setDescription(source.getDescription());
        target.setUser(userService.getUserByLogin(principal.getName()));
        target.setPrice(source.getPrice());
        target.setSize(source.getSize());
        target.setCloth(clothService.getCloth(clothData.getName(), (clothData.getSex().getName())));
        target.setPicture(source.getPicture());
        return target;
    }
}
