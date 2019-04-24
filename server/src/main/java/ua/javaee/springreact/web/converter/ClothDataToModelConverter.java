package ua.javaee.springreact.web.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.javaee.springreact.web.data.ClothData;
import ua.javaee.springreact.web.entity.Cloth;

/**
 * Created by kleba on 20.04.2019.
 */
@Component
public class ClothDataToModelConverter implements AbstractConverter<Cloth, ClothData> {
    @Autowired
    private ClothTypeDataToModelConverter clothTypeDataToModelConverter;
    @Autowired
    private SexDataToModelConverter sexDataToModelConverter;

    @Override
    public Cloth convert(ClothData source) {
        Cloth target = new Cloth();
        target.setName(source.getName());
        target.setClothType(clothTypeDataToModelConverter.convert(source.getClothTypeData()));
        target.setSex(sexDataToModelConverter.convert(source.getSex()));
        return target;
    }
}
