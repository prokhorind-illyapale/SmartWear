package ua.javaee.springreact.web.facade.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.javaee.springreact.web.converter.UserClothAttributeDataToModelConverter;
import ua.javaee.springreact.web.converter.UserClothAttributeModelToDataConverter;
import ua.javaee.springreact.web.data.UserClothAttributeData;
import ua.javaee.springreact.web.data.UserData;
import ua.javaee.springreact.web.entity.UserClothAttribute;
import ua.javaee.springreact.web.facade.UserClothAttributeFacade;
import ua.javaee.springreact.web.populator.UserClothAttributeDataToModelPopulator;
import ua.javaee.springreact.web.service.UserClothAttributeService;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

@Component
public class UserClothAttributeFacadeImpl implements UserClothAttributeFacade {

    @Autowired
    private UserClothAttributeService userClothAttributeService;
    @Autowired
    private UserClothAttributeModelToDataConverter converter;
    @Autowired
    private UserClothAttributeDataToModelConverter reverseConverter;
    @Autowired
    private UserClothAttributeDataToModelPopulator reversePopulator;

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
        userClothAttribute.setCode(userClothAttributeService.getLastRow() + 1l);
        return userClothAttributeService.save(userClothAttribute);
    }

    @Override
    public boolean update(UserClothAttributeData userClothAttributeData, Long code) {
        UserClothAttribute model = userClothAttributeService.get(code);
        if (isNull(model)) {
            return false;
        }
        reversePopulator.populate(userClothAttributeData, model);
        return userClothAttributeService.save(model);
    }

    @Override
    public boolean isUserClothAttributes(UserData userData, Long code) {
        UserClothAttribute model = userClothAttributeService.get(code);
        return !isNull(model) && userData.getLogin().equalsIgnoreCase(model.getUser().getLogin());
    }

    @Override
    public List<UserClothAttributeData> get(String userName, int pageNumber, int size) {
        List<UserClothAttribute> models = userClothAttributeService.get(userName, pageNumber, size);
        List<UserClothAttributeData> data = new ArrayList<>();
        if (isNull(models) || models.isEmpty()) {
            return null;
        }
        for (UserClothAttribute model : models) {
            data.add(converter.convert(model));
        }
        return data;
    }
}
