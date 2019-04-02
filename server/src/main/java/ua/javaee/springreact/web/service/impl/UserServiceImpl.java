package ua.javaee.springreact.web.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.javaee.springreact.web.data.UserData;
import ua.javaee.springreact.web.entity.Role;
import ua.javaee.springreact.web.entity.User;
import ua.javaee.springreact.web.populator.UserDataToUserModelPopulator;
import ua.javaee.springreact.web.repository.RoleRepository;
import ua.javaee.springreact.web.repository.UserRepository;
import ua.javaee.springreact.web.service.UserService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kleba on 09.02.2019.
 */
@Service
public class UserServiceImpl implements UserService {

    private static final String USER_ROLE = "USER";
    private static final int USER_EXISTS = 1;
    private static final String USER_WITH_NAME_WAS_NOT_FOUND = "User with name {} was not found";
    private static final String USER_WITH_NAME_ALREADY_EXISTS = "User with name {} was found";
    private static final String USER_WAS_UPDATED = "User {} was updated";

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired(required = true)
    private UserRepository userRepository;
    @Autowired(required = true)
    private RoleRepository roleRepository;
    @Autowired(required = true)
    private UserDataToUserModelPopulator userDataToUserModelPopulator;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    @Transactional
    public void userReg(UserData userData) {
        User user = new User();
        userDataToUserModelPopulator.populate(userData, user);
        user.setRole(roleRepository.findByName(USER_ROLE));
        userRepository.save(user);
    }

    @Override
    @Transactional
    public boolean isUserExists(String login) {
        Long number = userRepository.countByLogin(login);
        if (number.intValue() == USER_EXISTS) {
            logger.info(USER_WITH_NAME_ALREADY_EXISTS, login);
            return true;
        } else {
            logger.info(USER_WITH_NAME_WAS_NOT_FOUND, login);
            return false;
        }
    }

    @Override
    public User getUserByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    @Override
    public Role getRoleByLogin(String login) {
        return roleRepository.getRoleByLogin(login);
    }

    @Override
    public Role getRoleByName(String roleName) {
        return roleRepository.findByName(roleName);
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        Iterable<User> usersFromDb = userRepository.findAll();
        usersFromDb.forEach(users::add);
        return users;
    }

    @Override
    @Transactional
    public void deleteUserByLogin(String login) {
        userRepository.deleteByLogin(login);
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        userRepository.save(user);
        logger.info(USER_WAS_UPDATED, user.getLogin());
    }

    public String convertPassword(String password) {
        return bCryptPasswordEncoder.encode(password);
    }

    public boolean isPasswordMatches(String nonEncrypred, String encrypted) {
        return bCryptPasswordEncoder.matches(nonEncrypred, encrypted);
    }

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public RoleRepository getRoleRepository() {
        return roleRepository;
    }
}
