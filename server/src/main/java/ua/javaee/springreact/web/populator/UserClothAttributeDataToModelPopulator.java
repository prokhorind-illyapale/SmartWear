package ua.javaee.springreact.web.populator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.javaee.springreact.web.data.UserClothAttributeData;
import ua.javaee.springreact.web.entity.UserClothAttribute;
import ua.javaee.springreact.web.service.ClothService;

@Component
public class UserClothAttributeDataToModelPopulator implements AbstractPopulator<UserClothAttributeData, UserClothAttribute> {

    @Autowired
    private ClothService clothService;

    @Override
    public void populate(UserClothAttributeData source, UserClothAttribute target) {
        target.setSize(source.getSize());
        target.setPrice(source.getPrice());
        target.setPicture(source.getPicture());
        target.setCloth(clothService.getCloth(source.getCloth().getName(), source.getCloth().getSex().getName()));
        target.setColor(source.getColor());
        target.setDescription(source.getDescription());
        target.setPublic(source.isPublic());
    }
}
