package ua.javaee.springreact.web.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ua.javaee.springreact.web.data.UserClothAttributeData;
import ua.javaee.springreact.web.form.lookforms.ClothForm;
import ua.javaee.springreact.web.form.lookforms.UserClothAttributeDataForm;

/**
 * Created by kleba on 24.02.2019.
 */
@Component
public class UserClothAttributeDataToFormConverter implements AbstractConverter<UserClothAttributeDataForm, UserClothAttributeData> {

    @Autowired
    @Qualifier("clothDataToFormConverter")
    private AbstractConverter clothDataToFormConverter;

    @Override
    public UserClothAttributeDataForm convert(UserClothAttributeData source) {
        UserClothAttributeDataForm target = new UserClothAttributeDataForm();
        target.setDescription(source.getDescription());
        target.setCode(source.getCode());
        target.setColor(source.getColor());
        target.setPicture(source.getPicture());
        target.setPrice(source.getPrice());
        target.setUserLogin(source.getUserData().getLogin());
        target.setSize(source.getSize());
        target.setCloth((ClothForm) clothDataToFormConverter.convert(source.getCloth()));
        target.setPublic(source.isPublic());

        return target;
    }
}
