package ua.javaee.springreact.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.javaee.springreact.web.dao.RoleDao;
import ua.javaee.springreact.web.dao.UserDao;
import ua.javaee.springreact.web.data.UserData;
import ua.javaee.springreact.web.entity.Role;
import ua.javaee.springreact.web.entity.User;
import ua.javaee.springreact.web.populator.UserDataToUserModelPopulator;
import ua.javaee.springreact.web.service.UserService;

/**
 * Created by kleba on 09.02.2019.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired(required = true)
    private UserDao userDao;
    @Autowired(required = true)
    private RoleDao roleDao;
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

    @Override
    public User getUserByLogin(String login) {
        return userDao.getUserByLogin(login);
    }

    @Override
    public Role getRoleByLogin(String login) {
        return roleDao.getRoleByLogin(login);
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public void setRoleDao(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    public RoleDao getRoleDao() {
        return roleDao;
    }
}
