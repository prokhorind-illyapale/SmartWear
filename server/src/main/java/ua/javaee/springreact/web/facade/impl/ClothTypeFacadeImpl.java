package ua.javaee.springreact.web.facade.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.javaee.springreact.web.converter.ClothTypeModelToDataConverter;
import ua.javaee.springreact.web.data.ClothTypeData;
import ua.javaee.springreact.web.entity.ClothType;
import ua.javaee.springreact.web.facade.ClothTypeFacade;
import ua.javaee.springreact.web.service.ClothTypeService;

import java.util.List;

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

    private ClothTypeData convert(ClothType clothType) {
        return converter.convert(clothType);
    }
}
