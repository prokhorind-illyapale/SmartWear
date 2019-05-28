package ua.javaee.springreact.web.facade.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.javaee.springreact.web.converter.UserClothAttributeDataToModelConverter;
import ua.javaee.springreact.web.converter.UserClothAttributeModelToDataConverter;
import ua.javaee.springreact.web.data.UserClothAttributeData;
import ua.javaee.springreact.web.data.UserData;
import ua.javaee.springreact.web.entity.UserClothAttribute;
import ua.javaee.springreact.web.facade.UserClothAttributeFacade;
import ua.javaee.springreact.web.service.UserClothAttributeService;

import static java.util.Objects.isNull;

@Component
public class UserClothAttributeFacadeImpl implements UserClothAttributeFacade {

    @Autowired
    private UserClothAttributeService userClothAttributeService;
    @Autowired
    private UserClothAttributeModelToDataConverter converter;
    @Autowired
    private UserClothAttributeDataToModelConverter reverseConverter;


    public UserClothAttributeData get(Long code) {
        UserClothAttribute model = userClothAttributeService.get(code);
        return isNull(model) ? null : converter.convert(model);
    }

    @Override
    public void remove(Long code) {
        userClothAttributeService.remove(code);
    }

    @Override
    public boolean save(UserClothAttributeData userClothAttributeData) {
        UserClothAttribute userClothAttribute = reverseConverter.convert(userClothAttributeData);
        return userClothAttributeService.save(userClothAttribute);
    }

    @Override
    public boolean isUserClothAttributes(UserData userData, Long code) {
        UserClothAttribute model = userClothAttributeService.get(code);
        return !isNull(model) && userData.getLogin().equalsIgnoreCase(model.getUser().getLogin());
    }
}
