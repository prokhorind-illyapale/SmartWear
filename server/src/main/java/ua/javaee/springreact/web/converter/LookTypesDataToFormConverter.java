package ua.javaee.springreact.web.converter;

import org.springframework.stereotype.Component;
import ua.javaee.springreact.web.data.LookTypeData;
import ua.javaee.springreact.web.form.lookforms.LookTypeDataForm;

/**
 * Created by kleba on 24.02.2019.
 */
@Component
public class LookTypesDataToFormConverter implements AbstractConverter<LookTypeDataForm, LookTypeData> {
    @Override
    public LookTypeDataForm convert(LookTypeData source) {
        LookTypeDataForm target = new LookTypeDataForm();
        target.setName(source.getName());
        return target;
    }
}
