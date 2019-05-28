package ua.javaee.springreact.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.javaee.springreact.web.entity.UserClothAttribute;
import ua.javaee.springreact.web.repository.UserClothAttributeRepository;
import ua.javaee.springreact.web.service.UserClothAttributeService;

@Service
public class UserClothAttributeServiceImpl implements UserClothAttributeService {

    @Autowired
    private UserClothAttributeRepository userClothAttributeRepository;

    public UserClothAttribute get(Long code) {
        return userClothAttributeRepository.findUserClothAttributeByCode(code);
    }

    @Override
    @Transactional
    public void remove(Long code) {
        userClothAttributeRepository.removeByCode(code);
    }

    @Override
    @Transactional
    public boolean save(UserClothAttribute userClothAttribute) {
        try {
            userClothAttributeRepository.save(userClothAttribute);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public long count() {
        return userClothAttributeRepository.count();
    }
}
