package ua.javaee.springreact.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.javaee.springreact.web.facade.UserFacade;
import ua.javaee.springreact.web.form.LoginForm;

import java.security.Principal;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static ua.javaee.springreact.web.util.controller.ControllerConstants.NO_RIGHTS_FOR_THIS_ACTION;
import static ua.javaee.springreact.web.util.error.ErrorHelper.processingErrors;
import static ua.javaee.springreact.web.util.error.ErrorTypes.PERMISSION_TYPE_ERROR;

/**
 * Created by kleba on 25.03.2019.
 */
@RestController
@RequestMapping(value = "/admin")
public class AdminController {
    @Autowired
    private UserFacade userFacade;

    @RequestMapping(value = "/change-user-password", method = POST)
    public ResponseEntity<?> changeUserPassword(@RequestBody LoginForm form, Principal principal) {
        if (userFacade.isUserHasAdminRights(principal.getName()) && userFacade.isUserExists(form.getLogin())) {
            userFacade.updatePassword(form.getLogin(), form.getPassword());
            return new ResponseEntity(OK);
        } else {
            return processingErrors(NO_RIGHTS_FOR_THIS_ACTION + principal.getName(), PERMISSION_TYPE_ERROR);
        }
    }
}
