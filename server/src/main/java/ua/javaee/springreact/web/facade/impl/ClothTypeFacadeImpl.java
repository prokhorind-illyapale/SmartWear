package ua.javaee.springreact.web.facade.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.javaee.springreact.web.converter.ClothTypeModelToDataConverter;
import ua.javaee.springreact.web.data.ClothTypeData;
import ua.javaee.springreact.web.entity.ClothType;
import ua.javaee.springreact.web.facade.ClothTypeFacade;
import ua.javaee.springreact.web.service.ClothTypeService;

import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

/**
 * Created by kleba on 07.04.2019.
 */
@Component
public class ClothTypeFacadeImpl implements ClothTypeFacade {

    @Autowired
    private ClothTypeService clothTypeService;
    @Autowired
    private ClothTypeModelToDataConverter converter;

    @Override
    public List<ClothTypeData> getAllClothTypes() {
        List<ClothType> clothTypes = clothTypeService.getAllClothTypes();
        return clothTypes.stream().map(this::convert).collect(toList());
    }

    @Override
    public ClothTypeData getClothTypeData(String name) {
        ClothType clothTypeModel = clothTypeService.findClothType(name);
        if (Objects.isNull(clothTypeModel)) {
            return null;
        }
        return convert(clothTypeModel);
    }

    @Override
    public void saveClothType(String name) {
        clothTypeService.saveClothType(name);
    }

    @Override
    public void removeClothType(String name) {
        clothTypeService.removeClothType(name);
    }

    private ClothTypeData convert(ClothType clothType) {
        return converter.convert(clothType);
    }
}
