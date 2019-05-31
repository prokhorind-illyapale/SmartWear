package ua.javaee.springreact.web.converter;

import org.springframework.stereotype.Component;
import ua.javaee.springreact.web.data.LookTypeData;
import ua.javaee.springreact.web.entity.LookType;

@Component
public class LookTypeDataToModelConverter implements AbstractConverter<LookType, LookTypeData> {
    @Override
    public LookType convert(LookTypeData source) {
        LookType target = new LookType();
        target.setName(source.getName());
        return target;
    }
}
