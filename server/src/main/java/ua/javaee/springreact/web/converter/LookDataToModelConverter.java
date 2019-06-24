package ua.javaee.springreact.web.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.javaee.springreact.web.data.LookData;
import ua.javaee.springreact.web.data.LookTypeData;
import ua.javaee.springreact.web.data.UserClothAttributeData;
import ua.javaee.springreact.web.entity.Look;
import ua.javaee.springreact.web.entity.LookType;
import ua.javaee.springreact.web.entity.UserClothAttribute;
import ua.javaee.springreact.web.service.LookService;
import ua.javaee.springreact.web.service.LookTypeService;
import ua.javaee.springreact.web.service.UserClothAttributeService;
import ua.javaee.springreact.web.service.UserService;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Objects.*;
import static java.util.stream.Collectors.*;

@Component
public class LookDataToModelConverter implements  AbstractConverter <Look, LookData> {

    @Autowired
    private LookService lookService;
    @Autowired
    private LookTypeDataToModelConverter lookTypeDataToModelConverter;
    @Autowired
    private LookTypeService lookTypeService;
    @Autowired
    private UserClothAttributeService userClothAttributeService;
    @Autowired
    private UserService userService;
    @Autowired
    private CommentDataToModelConverter commentDataToModelConverter;

    @Override
    public Look convert(LookData source) {
       Look target = lookService.findByCode(source.getCode());
       if(isNull(target)){
           target = new Look();
       }
       target.setActive(source.isActive());
       target.setDescription(source.getDescription());
       target.setComments(commentDataToModelConverter.convertAll(source.getComments()));
       target.setLikes(source.getLikes());
       target.setLookTypes(getLookTypes(source.getLookTypes()));
       target.setMaxTemperature(source.getMaxTemperature());
       target.setMinTemperature(source.getMinTemperature());
       target.setPicture(source.getPicture());
       target.setUser(userService.getUserByLogin(source.getUser().getLogin()));
       target.setUserClothAttributes(getUserClothAttributes(source.getUserClothAttributes()));
       return target;
    }

    private Set<LookType> getLookTypes(Set<LookTypeData> lookTypeData){
        return lookTypeData.stream().map(look -> lookTypeService.getLookTypeByName(look.getName())).collect(toSet());
    }

    private Set<UserClothAttribute> getUserClothAttributes(Set<UserClothAttributeData> userClothAttributeDataSet){
        return userClothAttributeDataSet.stream().map(uca -> userClothAttributeService.get(uca.getCode())).collect(toSet());
    }
}
