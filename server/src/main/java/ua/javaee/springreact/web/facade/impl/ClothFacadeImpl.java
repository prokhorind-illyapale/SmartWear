package ua.javaee.springreact.web.facade.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.javaee.springreact.web.converter.ClothModelToDataConverter;
import ua.javaee.springreact.web.data.ClothData;
import ua.javaee.springreact.web.entity.Cloth;
import ua.javaee.springreact.web.facade.ClothFacade;
import ua.javaee.springreact.web.service.ClothService;

import java.util.List;

import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toList;

/**
 * Created by kleba on 16.04.2019.
 */
@Component
public class ClothFacadeImpl implements ClothFacade {
    @Autowired
    private ClothService clothService;
    @Autowired
    private ClothModelToDataConverter converter;

    @Override
    public List<ClothData> getAllClothes() {
        List<Cloth> clothModelList = clothService.getAllClothes();
        return clothModelList.stream().map(this::convert).collect(toList());
    }

    @Override
    public void saveCloth(ClothData cloth) {
        clothService.saveCloth(cloth);
    }

    @Override
    public ClothData getCloth(String clothName, String sexType) {
        Cloth cloth = clothService.getCloth(clothName, sexType);
        if (isNull(cloth)) {
            return null;
        }
        return converter.convert(cloth);
    }

    @Override
    public void deleteCloth(String clothName, String sexType) {
        clothService.deleteCloth(clothName, sexType);
    }

    private ClothData convert(Cloth cloth) {
        return converter.convert(cloth);
    }
}
