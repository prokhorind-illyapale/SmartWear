package ua.javaee.springreact.web.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.javaee.springreact.web.data.LookData;
import ua.javaee.springreact.web.data.LookTypeData;
import ua.javaee.springreact.web.data.UserClothAttributeData;
import ua.javaee.springreact.web.facade.UserClothAttributeFacade;
import ua.javaee.springreact.web.facade.UserFacade;
import ua.javaee.springreact.web.form.lookforms.LookForm;
import ua.javaee.springreact.web.form.lookforms.LookTypeDataForm;

import java.util.HashSet;
import java.util.Set;

import static java.util.stream.Collectors.toSet;
@Component
public class LookFormToDataConverter implements AbstractConverter <LookData, LookForm> {

    @Autowired
    private UserClothAttributeFacade userClothAttributeFacade;
    @Autowired
    private CommentFormToDataConverter commentFormToDataConverter;
    @Autowired
    private UserFacade userFacade;

    @Override
    public LookData convert(LookForm source) {
        LookData target = new LookData();
        target.setMaxTemperature(source.getMaxTemperature());
        target.setMinTemperature(source.getMinTemperature());
        target.setActive(source.isPublic());
        target.setDescription(source.getDescription());
        target.setLikes(source.getLikes());
        target.setLookTypes(getLookTypes(source.getLookTypes()));
        target.setComments(commentFormToDataConverter.convertAll(source.getComments()));
        target.setUserClothAttributes(getUserClothAttributes(source.getUserClothAttributes()));
        target.setUser(userFacade.getUserByLogin(source.getUserLogin()));
        return target;
    }

    private Set<LookTypeData> getLookTypes(Set<LookTypeDataForm> lookTypes) {
        Set<LookTypeData> lookTypeData = new HashSet<>();
        for(LookTypeDataForm lookTypeDataForm : lookTypes){
            LookTypeData lt = new LookTypeData();
            lt.setName(lookTypeDataForm.getName());
            lookTypeData.add(lt);
        }
        return lookTypeData;
    }

    private Set<UserClothAttributeData> getUserClothAttributes(Set<Long> codes){
       return codes.stream().map(userClothAttributeFacade::get).collect(toSet());
    }
}
