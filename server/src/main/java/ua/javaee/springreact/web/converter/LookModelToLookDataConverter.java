package ua.javaee.springreact.web.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ua.javaee.springreact.web.data.LookData;
import ua.javaee.springreact.web.data.LookTypeData;
import ua.javaee.springreact.web.data.UserClothAttributeData;
import ua.javaee.springreact.web.data.UserData;
import ua.javaee.springreact.web.entity.Look;
import ua.javaee.springreact.web.entity.LookType;
import ua.javaee.springreact.web.entity.UserClothAttribute;
import ua.javaee.springreact.web.populator.AbstractPopulator;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by kleba on 24.02.2019.
 */
@Component
public class LookModelToLookDataConverter implements AbstractConverter<LookData, Look> {

    @Autowired
    @Qualifier("userToUserDataPopulator")
    private AbstractPopulator userToUserDataPopulator;
    @Autowired
    @Qualifier("lookTypesModelToDataConverter")
    private AbstractConverter lookTypesModelToDataConverter;
    @Autowired
    @Qualifier("userClothAttributeModelToDataConverter")
    private AbstractConverter userClothAttributeModelToDataConverter;

    @Override
    public LookData convert(Look source) {
        LookData target = new LookData();
        UserData userData = new UserData();
        target.setLookTypes(getLookTypeDatas(source));
        userToUserDataPopulator.populate(source.getUser(), userData);
        target.setCode(source.getCode());
        target.setDescription(source.getDescription());
        target.setLikes(source.getLikes());
        target.setUser(userData);
        target.setUserClothAttributes(getUserClothAttributeDatas(source));
        target.setMinTemperature(source.getMinTemperature());
        target.setMaxTemperature(source.getMaxTemperature());
        return target;
    }

    private Set<UserClothAttributeData> getUserClothAttributeDatas(Look source) {
        Set<UserClothAttributeData> uaDatas = new HashSet<>();
        for (UserClothAttribute ua : source.getUserClothAttributes()) {
            UserClothAttributeData uaData = (UserClothAttributeData) userClothAttributeModelToDataConverter.convert(ua);
            uaDatas.add(uaData);
        }
        return uaDatas;
    }

    private Set<LookTypeData> getLookTypeDatas(Look source) {
        Set<LookTypeData> lookTypeDatas = new HashSet<>();
        for (LookType lookType : source.getLookTypes()) {
            LookTypeData lookTypeData = (LookTypeData) lookTypesModelToDataConverter.convert(lookType);
            lookTypeDatas.add(lookTypeData);
        }
        return lookTypeDatas;
    }
}