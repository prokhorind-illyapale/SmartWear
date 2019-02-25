package ua.javaee.springreact.web.converter;

import org.springframework.stereotype.Component;
import ua.javaee.springreact.web.data.LookTypeData;
import ua.javaee.springreact.web.entity.LookType;

/**
 * Created by kleba on 24.02.2019.
 */
@Component
public class LookTypesModelToDataConverter implements AbstractConverter<LookTypeData, LookType> {
    @Override
    public LookTypeData convert(LookType source) {
        LookTypeData target = new LookTypeData();
        target.setName(source.getName());
        return target;
    }
}
