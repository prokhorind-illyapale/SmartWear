package ua.javaee.springreact.web.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.javaee.springreact.web.entity.Look;
import ua.javaee.springreact.web.repository.LookRepository;
import ua.javaee.springreact.web.service.LookService;

import java.util.List;

import static java.util.Objects.isNull;

/**
 * Created by kleba on 24.02.2019.
 */
@Service
public class LookServiceImpl implements LookService {

    private Logger logger = LoggerFactory.getLogger(LookServiceImpl.class);

    @Autowired
    private LookRepository lookRepository;

    @Override
    public Look findByCode(String code) {
        return lookRepository.findByCode(code);
    }

    @Override
    public List<Look> findAllUserLooks(String login) {
        return lookRepository.findAllUserLooks(login);
    }

    public boolean isUserHasLookNumber(String code) {
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
    public boolean isPrincipalLook(String code, String login) {
        Look look = lookRepository.isPrincipalLook(code, login);
        if (isNull(look)) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    @Transactional
    public void deleteLookByCode(String code) {
        lookRepository.deleteLookByCode(code);
    }

    public boolean isLookPublic(String code) {
        return lookRepository.isLookPublic(code);
    }
}
