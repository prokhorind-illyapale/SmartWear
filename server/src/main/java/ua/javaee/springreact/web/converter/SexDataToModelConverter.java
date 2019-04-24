package ua.javaee.springreact.web.converter;

import org.springframework.stereotype.Component;
import ua.javaee.springreact.web.data.SexData;
import ua.javaee.springreact.web.entity.Sex;

/**
 * Created by kleba on 20.04.2019.
 */
@Component
public class SexDataToModelConverter implements AbstractConverter<Sex, SexData> {
    @Override
    public Sex convert(SexData source) {
        Sex sex = new Sex();
        sex.setName(source.getName());
        return sex;
    }
}
