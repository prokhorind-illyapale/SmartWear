package ua.javaee.springreact.web.service;

import ua.javaee.springreact.web.data.UserData;
import ua.javaee.springreact.web.form.RegistryUserForm;

/**
 * Created by kleba on 09.02.2019.
 */
public interface UserRegistryService {
    void userReg(UserData userData);
    boolean isUserExists(String login);
}
