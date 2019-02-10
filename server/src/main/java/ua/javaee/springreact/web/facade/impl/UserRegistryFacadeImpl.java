package ua.javaee.springreact.web.facade.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Component;
import ua.javaee.springreact.web.data.UserData;
import ua.javaee.springreact.web.entity.Role;
import ua.javaee.springreact.web.facade.UserRegistryFacade;
import ua.javaee.springreact.web.form.RegistryUserForm;
import ua.javaee.springreact.web.populator.AbstractPopulator;
import ua.javaee.springreact.web.populator.RegUserFormToUserDataPopulator;
import ua.javaee.springreact.web.service.UserRegistryService;

/**
 * Created by kleba on 09.02.2019.
 */
@Component
public class UserRegistryFacadeImpl implements UserRegistryFacade {

    @Autowired(required = true)
    private RegUserFormToUserDataPopulator reguserFormToUserDataPopulator;
    @Autowired(required = true)
    private UserRegistryService userRegistryService;

    @Override
    public void userReg(RegistryUserForm userForm) {
        UserData userData = new UserData();
        reguserFormToUserDataPopulator.populate(userForm,userData);
        userRegistryService.userReg(userData);
    }

    @Override
    public boolean isUserExists(String login) {
        return userRegistryService.isUserExists(login);
    }


    public void setReguserFormToUserDataPopulator(RegUserFormToUserDataPopulator reguserFormToUserDataPopulator) {
        this.reguserFormToUserDataPopulator = reguserFormToUserDataPopulator;
    }

    public void setUserRegistryService(UserRegistryService userRegistryService) {
        this.userRegistryService = userRegistryService;
    }

    public AbstractPopulator getReguserFormToUserDataPopulator() {
        return reguserFormToUserDataPopulator;
    }

    public UserRegistryService getUserRegistryService() {
        return userRegistryService;
    }
}
