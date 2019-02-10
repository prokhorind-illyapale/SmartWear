package ua.javaee.springreact.web.facade;

import ua.javaee.springreact.web.form.RegistryUserForm;

/**
 * Created by kleba on 09.02.2019.
 */
public interface UserRegistryFacade {

    void userReg(RegistryUserForm userForm);
    boolean isUserExists(String login);
}
