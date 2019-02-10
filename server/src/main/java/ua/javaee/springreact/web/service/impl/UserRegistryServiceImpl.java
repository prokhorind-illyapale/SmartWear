package ua.javaee.springreact.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.javaee.springreact.web.dao.UserDao;
import ua.javaee.springreact.web.data.UserData;
import ua.javaee.springreact.web.entity.User;
import ua.javaee.springreact.web.populator.UserDataToUserModelPopulator;
import ua.javaee.springreact.web.service.UserRegistryService;

/**
 * Created by kleba on 09.02.2019.
 */
@Service
public class UserRegistryServiceImpl implements UserRegistryService {

    @Autowired(required = true)
    private UserDao userDao;
    @Autowired(required = true)
    private UserDataToUserModelPopulator userDataToUserModelPopulator;

    @Override
    @Transactional
    public void userReg(UserData userData) {
        User user = new User();
        userDataToUserModelPopulator.populate(userData, user);
        userDao.userReg(user);
    }

    @Override
    @Transactional
    public boolean isUserExists(String login) {
        return userDao.isUserExists(login);
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public UserDao getUserDao() {
        return userDao;
    }
}
