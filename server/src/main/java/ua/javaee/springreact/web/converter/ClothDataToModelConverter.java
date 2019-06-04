package ua.javaee.springreact.web.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.javaee.springreact.web.data.ClothData;
import ua.javaee.springreact.web.entity.Cloth;
import ua.javaee.springreact.web.entity.ClothType;
import ua.javaee.springreact.web.entity.Sex;
import ua.javaee.springreact.web.repository.SexRepository;
import ua.javaee.springreact.web.service.ClothTypeService;

import static java.util.Objects.isNull;

/**
 * Created by kleba on 20.04.2019.
 */
@Component
public class ClothDataToModelConverter implements AbstractConverter<Cloth, ClothData> {
    @Autowired
    private ClothTypeDataToModelConverter clothTypeDataToModelConverter;
    @Autowired
    private SexDataToModelConverter sexDataToModelConverter;
    @Autowired
    private ClothTypeService clothTypeService;
    @Autowired
    private SexRepository sexRepository;

    @Override
    public Cloth convert(ClothData source) {
        Cloth target = new Cloth();
        target.setName(source.getName());
        ClothType clothType = clothTypeService.findClothType(source.getClothTypeData().getName());
        if (isNull(clothType)) {
            ClothType newClothType = clothTypeDataToModelConverter.convert(source.getClothTypeData());
            clothTypeService.saveClothType(newClothType);
            target.setClothType(newClothType);
        } else {
            target.setClothType(clothType);
        }
        Sex sex = sexRepository.getSexByName(source.getSex().getName());
        if (isNull(sex)) {
            Sex newSex = sexDataToModelConverter.convert(source.getSex());
            sexRepository.save(newSex);
            target.setSex(newSex);
        } else {
            target.setSex(sex);
        }
        return target;
    }
}
