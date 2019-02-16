package ua.javaee.springreact.web.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.javaee.springreact.web.data.UserData;
import ua.javaee.springreact.web.facade.UserFacade;
import ua.javaee.springreact.web.form.UserForm;
import ua.javaee.springreact.web.populator.UserDataToUserFormPopulator;

import java.security.Principal;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
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
    @Autowired
    private UserFacade userFacade;
    @Autowired
    private UserDataToUserFormPopulator userDataToUserFormPopulator;

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

    @RequestMapping(value = "/delete/{login}", method = DELETE)
    public ResponseEntity<?> deleteUserByAdmin(@PathVariable("login") String login, Principal principal) {
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

    private boolean isUserHasRights(String login, Principal principal) {
        return principal.getName().equalsIgnoreCase(login) || userFacade.isUserHasAdminRights(principal.getName());
    }


}
