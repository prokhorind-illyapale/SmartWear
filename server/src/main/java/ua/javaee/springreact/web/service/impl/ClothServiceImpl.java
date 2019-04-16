package ua.javaee.springreact.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.javaee.springreact.web.entity.Cloth;
import ua.javaee.springreact.web.repository.ClothRepository;
import ua.javaee.springreact.web.service.ClothService;

import java.util.List;

/**
 * Created by kleba on 16.04.2019.
 */
@Service
public class ClothServiceImpl implements ClothService {
    @Autowired
    private ClothRepository clothRepository;

    public List<Cloth> getAllClothes() {
        return clothRepository.findAll();
    }
}
