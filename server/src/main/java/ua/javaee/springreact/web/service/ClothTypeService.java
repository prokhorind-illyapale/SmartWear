package ua.javaee.springreact.web.service;

import ua.javaee.springreact.web.entity.ClothType;

import java.util.List;

/**
 * Created by kleba on 07.04.2019.
 */
public interface ClothTypeService {
    List<ClothType> getAllClothTypes();

    ClothType findClothType(String name);

    void saveClothType(String name);

    void removeClothType(String name);

    void saveClothType(ClothType clothType);
}
