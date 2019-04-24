package ua.javaee.springreact.web.service;

import ua.javaee.springreact.web.data.ClothData;
import ua.javaee.springreact.web.entity.Cloth;

import java.util.List;

/**
 * Created by kleba on 16.04.2019.
 */
public interface ClothService {
    List<Cloth> getAllClothes();

    void saveCloth(ClothData cloth);

    Cloth getCloth(String clothName, String sexType);

    void deleteCloth(String clothName, String sexType);
}
