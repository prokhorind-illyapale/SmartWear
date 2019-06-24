package ua.javaee.springreact.web.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.javaee.springreact.web.converter.LookDataToModelConverter;
import ua.javaee.springreact.web.data.LookData;
import ua.javaee.springreact.web.entity.Look;
import ua.javaee.springreact.web.entity.User;
import ua.javaee.springreact.web.entity.UserClothAttribute;
import ua.javaee.springreact.web.repository.LookRepository;
import ua.javaee.springreact.web.repository.UserRepository;
import ua.javaee.springreact.web.service.LookService;

import java.util.List;
import java.util.Set;

import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toList;

/**
 * Created by kleba on 24.02.2019.
 */
@Service
public class LookServiceImpl implements LookService {

    private Logger logger = LoggerFactory.getLogger(LookServiceImpl.class);

    @Autowired
    private LookRepository lookRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LookDataToModelConverter lookDataToModelConverter;

    @Override
    public Look findByCode(long code) {
        return lookRepository.findByCode(code);
    }

    @Override
    public List<Look> findAllUserLooks(String login) {
        return lookRepository.findAllUserLooks(login);
    }

    public boolean isUserHasLookNumber(long code) {
        try {
            Look look = lookRepository.findByCode(code);
            if (!isNull(look)) {
                return true;
            } else {
                return false;
            }
        } catch (NumberFormatException e) {
            logger.error("Wrong code format:" + e.getMessage());
            return false;
        } catch (Exception e) {
            logger.error("Something went wrong:" + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean isPrincipalLook(long code, String login) {
        Look look = lookRepository.isPrincipalLook(code, login);
        if (isNull(look)) {
            return false;
        } else {
            return true;
        }
    }

    public void removeDeletedUserClothAttributesFromLooks(List<UserClothAttribute> userClothAttributes) {
        List<Long> ids = userClothAttributes.stream().map(UserClothAttribute::getCode).collect(toList());
        List<Look> looks = lookRepository.findAllLooksByUserAttributes(ids);
        remove(userClothAttributes, looks);
    }

    private void remove(List<UserClothAttribute> userClothAttributes, List<Look> looks) {
        for (Look look : looks) {
            for (UserClothAttribute userClothAttribute : userClothAttributes) {
                Set<UserClothAttribute> userClothAttributeSet = look.getUserClothAttributes();
                if (userClothAttributeSet.contains(userClothAttribute)) {
                    userClothAttributeSet.remove(userClothAttribute);
                }
            }
        }
        lookRepository.saveAll(looks);
    }


    @Override
    @Transactional
    public void deleteLookByCode(long code) {
        lookRepository.deleteLookByCode(code);
    }

    @Override
    @Transactional
    public void save(LookData lookData) {
        Look look = lookDataToModelConverter.convert(lookData);
        look.setCode(lookData.getCode());
        lookRepository.save(look);
    }

    @Override
    @Transactional
    public boolean addLike(long code, String login) {
        User user = userRepository.findByLogin(login);
        Look look = lookRepository.findByCode(code);
        boolean isUserAdded = look.getUsers().add(user);
        boolean isLookAdded = user.getLookLikes().add(look);
        userRepository.save(user);
        lookRepository.save(look);
        return isLookAdded && isUserAdded;
    }

    @Override
    @Transactional
    public boolean removeLike(String login, long code) {
        User user = userRepository.findByLogin(login);
        Look look = lookRepository.findByCode(code);
        boolean isUserRemoved = look.getUsers().remove(user);
        boolean isLookRemoved = user.getLookLikes().remove(look);
        userRepository.save(user);
        lookRepository.save(look);
        return isLookRemoved && isUserRemoved;
    }

    @Override
    public Long getLastRow() {
        return isNull(lookRepository.getLastRow()) ? 0l : lookRepository.getLastRow();
    }

    public boolean isLookPublic(long code) {
        return lookRepository.isLookPublic(code);
    }

}
