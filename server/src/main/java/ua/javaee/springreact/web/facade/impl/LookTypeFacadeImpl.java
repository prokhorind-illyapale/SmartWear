package ua.javaee.springreact.web.facade.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.javaee.springreact.web.converter.LookTypeDataToModelConverter;
import ua.javaee.springreact.web.converter.LookTypesModelToDataConverter;
import ua.javaee.springreact.web.data.LookTypeData;
import ua.javaee.springreact.web.entity.LookType;
import ua.javaee.springreact.web.facade.LookTypeFacade;
import ua.javaee.springreact.web.service.LookTypeService;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

@Component
public class LookTypeFacadeImpl implements LookTypeFacade {

    @Autowired
    private LookTypeService lookTypeService;
    @Autowired
    private LookTypesModelToDataConverter lookTypesModelToDataConverter;
    @Autowired
    private LookTypeDataToModelConverter reverseConverter;

    @Override
    public boolean isLookTypeExists(String name) {
        LookType lookTypeModel = lookTypeService.getLookTypeByName(name);
        if (isNull(lookTypeModel)) {
            return false;
        }
        return true;
    }

    @Override
    public List<LookTypeData> getAllLookTypes() {
        List<LookType> models = lookTypeService.getAllLookTypes();
        List<LookTypeData> lookTypes = new ArrayList<>();
        if (isNull(models) || models.isEmpty()) {
            return null;
        }
        for (LookType model : models) {
            lookTypes.add(lookTypesModelToDataConverter.convert(model));
        }
        return lookTypes;
    }

    @Override
    public LookTypeData getLookTypeByName(String name) {
        LookType lookTypeModel = lookTypeService.getLookTypeByName(name);
        if (isNull(lookTypeModel)) {
            return null;
        }
        return lookTypesModelToDataConverter.convert(lookTypeModel);
    }

    @Override
    public boolean removeLookType(String name) {
        LookType lookTypeModel = lookTypeService.getLookTypeByName(name);
        if (isNull(lookTypeModel)) {
            return false;
        }
        return lookTypeService.removeLookType(lookTypeModel);
    }

    @Override
    public boolean saveLookType(LookTypeData lookType) {
        return lookTypeService.saveLookType(reverseConverter.convert(lookType));
    }
}
