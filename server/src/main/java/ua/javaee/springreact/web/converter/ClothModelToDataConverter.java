package ua.javaee.springreact.web.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ua.javaee.springreact.web.data.ClothData;
import ua.javaee.springreact.web.data.ClothTypeData;
import ua.javaee.springreact.web.data.SexData;
import ua.javaee.springreact.web.entity.Cloth;

/**
 * Created by kleba on 24.02.2019.
 */
@Component
public class ClothModelToDataConverter implements AbstractConverter<ClothData, Cloth> {
    @Autowired
    @Qualifier("sexModelToDataConverter")
    private AbstractConverter sexModelToDataConverter;

    @Autowired
    @Qualifier("clothTypeModelToDataConverter")
    private AbstractConverter clothTypeModelToDataConverter;

    @Override
    public ClothData convert(Cloth source) {
        ClothData target = new ClothData();
        target.setName(source.getName());
        target.setClothTypeData((ClothTypeData) clothTypeModelToDataConverter.convert(source.getClothType()));
        target.setSex((SexData) sexModelToDataConverter.convert(source.getSex()));
        return target;
    }
}
