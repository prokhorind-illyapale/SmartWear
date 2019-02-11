package ua.javaee.springreact.web.service;

import ua.javaee.springreact.web.data.UserData;
import ua.javaee.springreact.web.entity.Role;
import ua.javaee.springreact.web.entity.User;

/**
 * Created by kleba on 09.02.2019.
 */
public interface UserService {
    void userReg(UserData userData);
    boolean isUserExists(String login);

    User getUserByLogin(String login);

    Role getRoleByLogin(String login);
}
