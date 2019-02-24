package ua.javaee.springreact.web.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ua.javaee.springreact.web.data.ClothData;
import ua.javaee.springreact.web.form.lookforms.ClothForm;
import ua.javaee.springreact.web.form.lookforms.ClothTypeDataForm;
import ua.javaee.springreact.web.form.lookforms.SexDataForm;

/**
 * Created by kleba on 24.02.2019.
 */
@Component
public class ClothDataToFormConverter implements AbstractConverter<ClothForm, ClothData> {
    @Autowired
    @Qualifier("clothTypeDataToFormConverter")
    private AbstractConverter clothTypeDataToFormConverter;

    @Autowired
    @Qualifier("sexDataToFormConverter")
    private AbstractConverter sexDataToFormConverter;

    @Override
    public ClothForm convert(ClothData source) {
        ClothForm target = new ClothForm();
        target.setName(source.getName());
        target.setClothTypeData((ClothTypeDataForm) clothTypeDataToFormConverter.convert(source.getClothTypeData()));
        target.setSex((SexDataForm) sexDataToFormConverter.convert(source.getSex()));
        return target;
    }
}
