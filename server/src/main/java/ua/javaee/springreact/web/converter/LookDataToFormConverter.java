package ua.javaee.springreact.web.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ua.javaee.springreact.web.data.LookData;
import ua.javaee.springreact.web.data.LookTypeData;
import ua.javaee.springreact.web.data.UserClothAttributeData;
import ua.javaee.springreact.web.form.lookforms.LookForm;
import ua.javaee.springreact.web.form.lookforms.LookTypeDataForm;
import ua.javaee.springreact.web.form.lookforms.UserClothAttributeDataForm;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by kleba on 24.02.2019.
 */
@Component
public class LookDataToFormConverter implements AbstractConverter<LookForm, LookData> {
    @Autowired
    @Qualifier("lookTypesDataToFormConverter")
    private AbstractConverter lookTypesDataToFormConverter;

    @Autowired
    @Qualifier("userClothAttributeDataToFormConverter")
    private AbstractConverter userClothAttributeDataToFormConverter;

    @Override
    public LookForm convert(LookData source) {
        LookForm target = new LookForm();
        target.setDescription(source.getDescription());
        target.setCode(source.getCode());
        target.setLikes(source.getLikes());
        target.setPublic(source.isActive());
        target.setUserLogin(source.getUser().getLogin());
        target.setLookTypes(getLookTypeDataForms(source));
        target.setUserClothAttributes(getUserClothAttributeDataForms(source));
        target.setMinTemperature(source.getMinTemperature());
        target.setMaxTemperature(source.getMaxTemperature());
        return target;
    }

    private Set<UserClothAttributeDataForm> getUserClothAttributeDataForms(LookData source) {
        Set<UserClothAttributeDataForm> forms = new HashSet<>();
        for (UserClothAttributeData attribute : source.getUserClothAttributes()) {
            UserClothAttributeDataForm form = (UserClothAttributeDataForm) userClothAttributeDataToFormConverter.convert(attribute);
            forms.add(form);
        }
        return forms;
    }

    private Set<LookTypeDataForm> getLookTypeDataForms(LookData source) {
        Set<LookTypeDataForm> forms = new HashSet<>();
        for (LookTypeData data : source.getLookTypes()) {
            LookTypeDataForm form = (LookTypeDataForm) lookTypesDataToFormConverter.convert(data);
            forms.add(form);
        }
        return forms;
    }
}
