package ua.javaee.springreact.web.facade.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.javaee.springreact.web.data.RoleData;
import ua.javaee.springreact.web.data.UserData;
import ua.javaee.springreact.web.entity.Role;
import ua.javaee.springreact.web.entity.User;
import ua.javaee.springreact.web.facade.UserFacade;
import ua.javaee.springreact.web.form.RegistryUserForm;
import ua.javaee.springreact.web.populator.*;
import ua.javaee.springreact.web.service.UserService;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;
import static org.apache.logging.log4j.util.Strings.isNotBlank;


/**
 * Created by kleba on 09.02.2019.
 */
@Component
public class UserFacadeImpl implements UserFacade {

    private static final String ADMIN = "ADMIN";
    @Autowired(required = true)
    private RegUserFormToUserDataPopulator reguserFormToUserDataPopulator;
    @Autowired(required = true)
    private RoleToRoleDataPopulator roleToRoleDataPopulator;
    @Autowired(required = true)
    private UserToUserDataPopulator userToUserDataPopulator;
    @Autowired(required = true)
    private UserDataToUserModelPopulator userDataToUserModelPopulator;
    @Autowired(required = true)
    private UserService userService;

    private Logger logger = LoggerFactory.getLogger(UserFacadeImpl.class);

    @Override
    public void userReg(RegistryUserForm userForm) {
        UserData userData = new UserData();
        reguserFormToUserDataPopulator.populate(userForm, userData);
        userService.userReg(userData);
    }

    public void updatePassword(String login, String password) {
        User user = userService.getUserByLogin(login);
        user.setPassword(userService.convertPassword(password));
        userService.updateUser(user);
    }

    @Override
    public boolean isUserExists(String login) {
        return userService.isUserExists(login);
    }

    @Override
    public boolean isUserHasAdminRights(String login) {
        Role role = userService.getRoleByLogin(login);
        if (role == null) {
            return false;
        }
        RoleData roleData = new RoleData();
        roleToRoleDataPopulator.populate(role, roleData);

        if (roleData.getRoleName().equalsIgnoreCase(ADMIN)) {
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

    @Override
    public void deleteUserByLogin(String login) {
        userService.deleteUserByLogin(login);
        logger.info("User {} was deleted.", login);
    }

    @Override
    public List<UserData> getAllUsers() {
        List<User> users = userService.getAllUsers();
        List<UserData> userDataList = new ArrayList<>();
        for (User user : users) {
            UserData userData = new UserData();
            userToUserDataPopulator.populate(user, userData);
            userDataList.add(userData);
        }
        return userDataList;
    }

    @Override
    public void updateUser(UserData user, String login, String principalName) {
        if (isNotBlank(login)) {
            User userModel = userService.getUserByLogin(login);
            User oldValues = getOldValues(userModel);
            userDataToUserModelPopulator.populate(user, userModel);
            if (isUserHasAdminRights(principalName) && user.getUserRole() != null) {
                Role newRole = userService.getRoleByName(user.getUserRole().getRoleName());
                if (newRole != null) {
                    userModel.setRole(newRole);
                }
            }
            setOldValuesIfBlank(userModel, oldValues);
            userService.updateUser(userModel);
        } else {
            logger.info("Empty login");
        }

    }

    private void setOldValuesIfBlank(User userModel, User oldValues) {
        if (userModel == null) {
            userModel = oldValues;
            return;
        }

        if (userModel.getRole() == null) {
            userModel.setRole(oldValues.getRole());
        }
        if (userModel.getLogin() == null) {
            userModel.setLogin(oldValues.getLogin());
        }

        if (userModel.getEmail() == null) {
            userModel.setEmail(oldValues.getEmail());
        }

        if (userModel.getPassword() == null) {
            userModel.setPassword(oldValues.getPassword());
        }

        if (userModel.getCity() == null) {
            userModel.setCity(oldValues.getCity());
        }
    }

    private User getOldValues(User model) {
        User oldValues = new User();
        oldValues.setCity(model.getCity());
        oldValues.setEmail(model.getEmail());
        oldValues.setRole(model.getRole());
        oldValues.setPassword(model.getPassword());
        oldValues.setLogin(model.getLogin());
        return oldValues;
    }

    public boolean isPasswordMatches(String login, String password) {
        User user = userService.getUserByLogin(login);
        if (isNull(user)) {
            return false;
        }
        return userService.isPasswordMatches(password, user.getPassword());
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
