package ua.javaee.springreact.web.service;

import ua.javaee.springreact.web.data.UserData;
import ua.javaee.springreact.web.entity.Role;
import ua.javaee.springreact.web.entity.User;

import java.util.List;

/**
 * Created by kleba on 09.02.2019.
 */
public interface UserService {
    void userReg(UserData userData);

    boolean isUserExists(String login);

    User getUserByLogin(String login);

    Role getRoleByLogin(String login);

    List<Role> getAllUserRoles();

    Role getRoleByName(String roleName);

    List<User> getAllUsers();

    void deleteUserByLogin(String login);

    void updateUser(User user);

    String convertPassword(String password);

    boolean isPasswordMatches(String nonEncrypred, String encrypted);
}
