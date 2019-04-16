package ua.javaee.springreact.web.facade;

import ua.javaee.springreact.web.data.ClothTypeData;

import java.util.List;

/**
 * Created by kleba on 07.04.2019.
 */

public interface ClothTypeFacade {

    public List<ClothTypeData> getAllClothTypes();

    public ClothTypeData getClothTypeData(String name);

    public void saveClothType(String name);

    public void removeClothType(String name);
}
