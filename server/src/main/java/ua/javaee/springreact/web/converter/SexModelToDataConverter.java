package ua.javaee.springreact.web.converter;

import org.springframework.stereotype.Component;
import ua.javaee.springreact.web.data.SexData;
import ua.javaee.springreact.web.entity.Sex;

/**
 * Created by kleba on 24.02.2019.
 */
@Component
public class SexModelToDataConverter implements AbstractConverter<SexData, Sex> {

    @Override
    public SexData convert(Sex source) {
        SexData target = new SexData();
        target.setName(source.getName());
        return target;
    }
}
