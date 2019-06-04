package ua.javaee.springreact.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.javaee.springreact.web.entity.ClothType;
import ua.javaee.springreact.web.repository.ClothTypeRepository;
import ua.javaee.springreact.web.service.ClothTypeService;

import java.util.List;

/**
 * Created by kleba on 07.04.2019.
 */
@Service
public class ClothTypeServiceImpl implements ClothTypeService {
    @Autowired
    private ClothTypeRepository clothTypeRepository;

    @Override
    public List<ClothType> getAllClothTypes() {
        return clothTypeRepository.findAll();
    }

    public ClothType findClothType(String name) {
        return clothTypeRepository.findClothTypeByName(name);
    }

    public void saveClothType(String name) {
        ClothType clothType = new ClothType();
        clothType.setName(name);
        clothTypeRepository.save(clothType);
    }

    @Override
    public void removeClothType(String name) {
        ClothType clothType = clothTypeRepository.findClothTypeByName(name);
        clothTypeRepository.delete(clothType);
    }

    @Override
    public void saveClothType(ClothType clothType) {
        clothTypeRepository.save(clothType);
    }
}
