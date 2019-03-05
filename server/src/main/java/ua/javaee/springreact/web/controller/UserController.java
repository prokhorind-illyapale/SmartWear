package ua.javaee.springreact.web.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.javaee.springreact.web.data.UserData;
import ua.javaee.springreact.web.facade.UserFacade;
import ua.javaee.springreact.web.form.UserForm;
import ua.javaee.springreact.web.populator.UserDataToUserFormPopulator;
import ua.javaee.springreact.web.populator.UserFormToUserDataPopulator;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.*;
import static ua.javaee.springreact.web.util.error.ErrorHelper.processingErrors;
import static ua.javaee.springreact.web.util.error.ErrorTypes.PERMISSION_TYPE_ERROR;
import static ua.javaee.springreact.web.util.error.ErrorTypes.VALIDATION_TYPE_ERROR;

/**
 * Created by kleba on 11.02.2019.
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {

    private static final String USER_NOT_FOUND = "User not found for login:";
    private static final String NO_RIGHTS_FOR_THIS_ACTION = "No rights for this action:";
    private static final String YOU_HAVE_NO_RIGHTS_TO_CHANGE_USER_ROLE = "You have no rights to change User role:";
    @Autowired
    private UserFacade userFacade;
    @Autowired
    private UserDataToUserFormPopulator userDataToUserFormPopulator;
    @Autowired
    private UserFormToUserDataPopulator userFormToUserDataPopulator;

    @RequestMapping(value = "/get/{login}", method = GET)
    public ResponseEntity<?> getUserByLogin(@PathVariable("login") String login, Principal principal) {
        if (isUserHasRights(login, principal)) {
            if (userFacade.isUserExists(login)) {
                UserData userData = userFacade.getUserByLogin(login);
                UserForm userForm = new UserForm();
                userDataToUserFormPopulator.populate(userData, userForm);
                return new ResponseEntity(userForm, OK);
            } else {
                return processingErrors(USER_NOT_FOUND + login, VALIDATION_TYPE_ERROR);
            }
        } else {
            return processingErrors(NO_RIGHTS_FOR_THIS_ACTION + login, PERMISSION_TYPE_ERROR);
        }
    }

    @RequestMapping(value = "/get/all", method = GET)
    public ResponseEntity<?> getAllUsers(Principal principal) {
        if (userFacade.isUserHasAdminRights(principal.getName())) {
            List<UserData> users = userFacade.getAllUsers();
            List<UserForm> userForms = new ArrayList<>();
            for (UserData user : users) {
                UserForm userForm = new UserForm();
                userDataToUserFormPopulator.populate(user, userForm);
                userForms.add(userForm);
            }
            return new ResponseEntity(userForms, OK);
        } else {
            return processingErrors(NO_RIGHTS_FOR_THIS_ACTION + principal.getName(), PERMISSION_TYPE_ERROR);
        }
    }

    @RequestMapping(value = "/delete/{login}", method = DELETE)
    public ResponseEntity<?> deleteUserByLogin(@PathVariable("login") String login, Principal principal) {
        if (isUserHasRights(login, principal)) {
            if (userFacade.isUserExists(login)) {
                userFacade.deleteUserByLogin(login);
                return new ResponseEntity(OK);
            } else {
                return processingErrors(USER_NOT_FOUND + login, VALIDATION_TYPE_ERROR);
            }
        } else {
            return processingErrors(NO_RIGHTS_FOR_THIS_ACTION + login, PERMISSION_TYPE_ERROR);
        }
    }

    @RequestMapping(value = "/update/{login}", method = PUT)
    public ResponseEntity<?> updateUserByLogin(@RequestBody UserForm userForm, @PathVariable("login") String login, Principal principal) {
        if (isUserHasRights(login, principal)) {
            if (userFacade.isUserExists(login)) {
                if (userFacade.isUserHasAdminRights(principal.getName()) || (userForm.getUserRole() == null || "USER".equalsIgnoreCase(userForm.getUserRole().getRoleName()))) {
                    UserData user = new UserData();
                    userFormToUserDataPopulator.populate(userForm, user);
                    userFacade.updateUser(user, login);
                    return new ResponseEntity(HttpStatus.OK);
                } else {
                    return processingErrors(YOU_HAVE_NO_RIGHTS_TO_CHANGE_USER_ROLE + login, PERMISSION_TYPE_ERROR);
                }
            } else {
                return processingErrors(USER_NOT_FOUND + login, VALIDATION_TYPE_ERROR);
            }
        } else {
            return processingErrors(NO_RIGHTS_FOR_THIS_ACTION + login, PERMISSION_TYPE_ERROR);
        }
    }

    @RequestMapping(value = "/get/me", method = GET)
    public ResponseEntity<?> getSessionUser(Principal principal) {
        UserData userData = userFacade.getUserByLogin(principal.getName());
        UserForm userForm = new UserForm();
        userDataToUserFormPopulator.populate(userData, userForm);
        return new ResponseEntity(userForm, OK);

    }

    private boolean isUserHasRights(String login, Principal principal) {
        return principal.getName().equalsIgnoreCase(login) || userFacade.isUserHasAdminRights(principal.getName());
    }


}
