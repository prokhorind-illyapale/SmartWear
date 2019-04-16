package ua.javaee.springreact.web.service;

import ua.javaee.springreact.web.entity.Cloth;

import java.util.List;

/**
 * Created by kleba on 16.04.2019.
 */
public interface ClothService {
    List<Cloth> getAllClothes();
}
