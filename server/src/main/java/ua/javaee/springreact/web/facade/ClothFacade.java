package ua.javaee.springreact.web.facade;

import ua.javaee.springreact.web.data.ClothData;

import java.util.List;

/**
 * Created by kleba on 16.04.2019.
 */
public interface ClothFacade {
    List<ClothData> getAllClothes();
}
