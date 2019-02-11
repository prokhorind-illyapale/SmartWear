package ua.javaee.springreact.web.dao;

import ua.javaee.springreact.web.entity.User;

/**
 * Created by kleba on 09.02.2019.
 */
public interface UserDao {
    void userReg(User userModel);

    boolean isUserExists(String login);

    User getUserByLogin(String login);
}
