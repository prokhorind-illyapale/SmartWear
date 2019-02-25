package ua.javaee.springreact.web.converter;

import org.springframework.stereotype.Component;
import ua.javaee.springreact.web.data.ClothTypeData;
import ua.javaee.springreact.web.form.lookforms.ClothTypeDataForm;

/**
 * Created by kleba on 24.02.2019.
 */
@Component
public class ClothTypeDataToFormConverter implements AbstractConverter<ClothTypeDataForm, ClothTypeData> {
    @Override
    public ClothTypeDataForm convert(ClothTypeData source) {
        ClothTypeDataForm target = new ClothTypeDataForm();
        target.setName(source.getName());
        return target;
    }
}
