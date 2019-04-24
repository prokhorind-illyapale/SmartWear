package ua.javaee.springreact.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.javaee.springreact.web.converter.ClothDataToModelConverter;
import ua.javaee.springreact.web.data.ClothData;
import ua.javaee.springreact.web.entity.Cloth;
import ua.javaee.springreact.web.entity.UserClothAttribute;
import ua.javaee.springreact.web.repository.ClothRepository;
import ua.javaee.springreact.web.repository.LookRepository;
import ua.javaee.springreact.web.repository.UserClothAttributeRepository;
import ua.javaee.springreact.web.service.ClothService;
import ua.javaee.springreact.web.service.LookService;

import java.util.List;

/**
 * Created by kleba on 16.04.2019.
 */
@Service
public class ClothServiceImpl implements ClothService {
    @Autowired
    private ClothRepository clothRepository;
    @Autowired
    private UserClothAttributeRepository userClothAttributeRepository;
    @Autowired
    private LookRepository lookRepository;
    @Autowired
    private ClothDataToModelConverter clothDataToModelConverter;
    @Autowired
    private LookService lookService;

    @Override
    @Transactional
    public List<Cloth> getAllClothes() {
        return clothRepository.findAll();
    }

    @Override
    @Transactional
    public void saveCloth(ClothData cloth) {
        Cloth model = clothDataToModelConverter.convert(cloth);
        clothRepository.save(model);
    }

    @Override
    @Transactional
    public Cloth getCloth(String clothName, String sexType) {
        return clothRepository.findCloth(clothName, sexType);
    }

    @Override
    @Transactional
    public void deleteCloth(String clothName, String sexType) {
        Cloth cloth = clothRepository.findCloth(clothName, sexType);
        List<UserClothAttribute> userClothAttributes = userClothAttributeRepository.findUserClothAttributeByCloth(cloth.getName());
        lookService.removeDeletedUserClothAttributesFromLooks(userClothAttributes);
        userClothAttributeRepository.deleteAll(userClothAttributes);
        clothRepository.delete(cloth);
    }
}
