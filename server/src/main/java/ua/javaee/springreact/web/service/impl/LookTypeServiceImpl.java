package ua.javaee.springreact.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.javaee.springreact.web.entity.LookType;
import ua.javaee.springreact.web.repository.LookTypeRepository;
import ua.javaee.springreact.web.service.LookTypeService;

import java.util.List;

@Service
public class LookTypeServiceImpl implements LookTypeService {

    @Autowired
    private LookTypeRepository lookTypeRepository;

    @Override
    public List<LookType> getAllLookTypes() {
        return lookTypeRepository.findAll();
    }

    @Override
    public LookType getLookTypeByName(String name) {
        return lookTypeRepository.findClothTypeByName(name);
    }

    public boolean removeLookType(LookType lookType) {
        lookTypeRepository.delete(lookType);
        return true;
    }

    @Override
    public boolean saveLookType(LookType lookType) {
        lookTypeRepository.save(lookType);
        return true;
    }
}
