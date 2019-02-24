package ua.javaee.springreact.web.converter;

import org.springframework.stereotype.Component;
import ua.javaee.springreact.web.data.ClothTypeData;
import ua.javaee.springreact.web.entity.ClothType;

/**
 * Created by kleba on 24.02.2019.
 */
@Component
public class ClothTypeModelToDataConverter implements AbstractConverter<ClothTypeData, ClothType> {

    @Override
    public ClothTypeData convert(ClothType source) {
        ClothTypeData target = new ClothTypeData();
        target.setName(source.getName());
        return target;
    }
}
