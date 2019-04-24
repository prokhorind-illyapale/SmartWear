package ua.javaee.springreact.web.converter;

import org.springframework.stereotype.Component;
import ua.javaee.springreact.web.data.ClothTypeData;
import ua.javaee.springreact.web.entity.ClothType;

/**
 * Created by kleba on 20.04.2019.
 */
@Component
public class ClothTypeDataToModelConverter implements AbstractConverter<ClothType, ClothTypeData> {

    @Override
    public ClothType convert(ClothTypeData source) {
        ClothType clothType = new ClothType();
        clothType.setName(source.getName());
        return clothType;
    }
}
