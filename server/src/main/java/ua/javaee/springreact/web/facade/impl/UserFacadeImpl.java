package ua.javaee.springreact.web.facade.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.javaee.springreact.web.data.RoleData;
import ua.javaee.springreact.web.data.UserData;
import ua.javaee.springreact.web.entity.Role;
import ua.javaee.springreact.web.entity.User;
import ua.javaee.springreact.web.facade.UserFacade;
import ua.javaee.springreact.web.form.RegistryUserForm;
import ua.javaee.springreact.web.populator.AbstractPopulator;
import ua.javaee.springreact.web.populator.RegUserFormToUserDataPopulator;
import ua.javaee.springreact.web.populator.RoleToRoleDataPopulator;
import ua.javaee.springreact.web.populator.UserToUserDataPopulator;
import ua.javaee.springreact.web.service.UserService;

/**
 * Created by kleba on 09.02.2019.
 */
@Component
public class UserFacadeImpl implements UserFacade {

    @Autowired(required = true)
    private RegUserFormToUserDataPopulator reguserFormToUserDataPopulator;
    @Autowired(required = true)
    private RoleToRoleDataPopulator roleToRoleDataPopulator;
    @Autowired(required = true)
    private UserToUserDataPopulator userToUserDataPopulator;
    @Autowired(required = true)
    private UserService userService;


    @Override
    public void userReg(RegistryUserForm userForm) {
        UserData userData = new UserData();
        reguserFormToUserDataPopulator.populate(userForm,userData);
        userService.userReg(userData);
    }

    @Override
    public boolean isUserExists(String login) {
        return userService.isUserExists(login);
    }

    @Override
    public boolean isUserHasRights(String login) {
        Role role = userService.getRoleByLogin(login);
        if (role == null) {
            return false;
        }
        RoleData roleData = new RoleData();
        roleToRoleDataPopulator.populate(role, roleData);

        if (roleData.getRoleName().equalsIgnoreCase("ADMIN")) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public UserData getUserByLogin(String login) {
        User user = userService.getUserByLogin(login);
        if (user != null) {
            UserData userData = new UserData();
            userToUserDataPopulator.populate(user, userData);
            return userData;
        } else {
            return null;
        }
    }


    public void setReguserFormToUserDataPopulator(RegUserFormToUserDataPopulator reguserFormToUserDataPopulator) {
        this.reguserFormToUserDataPopulator = reguserFormToUserDataPopulator;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public AbstractPopulator getReguserFormToUserDataPopulator() {
        return reguserFormToUserDataPopulator;
    }

    public UserService getUserService() {
        return userService;
    }

}
