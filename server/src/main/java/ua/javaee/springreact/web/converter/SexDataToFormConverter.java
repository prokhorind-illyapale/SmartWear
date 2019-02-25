package ua.javaee.springreact.web.converter;

import org.springframework.stereotype.Component;
import ua.javaee.springreact.web.data.SexData;
import ua.javaee.springreact.web.form.lookforms.SexDataForm;

/**
 * Created by kleba on 24.02.2019.
 */
@Component
public class SexDataToFormConverter implements AbstractConverter<SexDataForm, SexData> {
    @Override
    public SexDataForm convert(SexData source) {
        SexDataForm target = new SexDataForm();
        target.setName(source.getName());
        return target;
    }
}
