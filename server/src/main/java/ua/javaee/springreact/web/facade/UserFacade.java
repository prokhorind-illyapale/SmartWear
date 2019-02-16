package ua.javaee.springreact.web.facade;

import ua.javaee.springreact.web.data.UserData;
import ua.javaee.springreact.web.form.RegistryUserForm;

/**
 * Created by kleba on 09.02.2019.
 */
public interface UserFacade {

    void userReg(RegistryUserForm userForm);
    boolean isUserExists(String login);

    boolean isUserHasAdminRights(String login);

    UserData getUserByLogin(String login);

    void deleteUserByLogin(String login);
}
