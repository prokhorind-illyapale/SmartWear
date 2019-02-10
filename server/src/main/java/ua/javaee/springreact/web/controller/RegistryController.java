package ua.javaee.springreact.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.javaee.springreact.web.facade.UserRegistryFacade;
import ua.javaee.springreact.web.form.RegistryUserForm;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created by kleba on 09.02.2019.
 */
@RestController
public class RegistryController {

    private static final String VALIDATION_ERROR_MESSAGE = "Validation Error";
    private static final String SUCCESS_FOR_USER = "Success for user:";
    private static final String USER_ALREADY_EXSISTS_ERROR_MESSAGE = " user already exsists";

    @Autowired
    private UserRegistryFacade userRegistryFacade;

    @RequestMapping(value = "/registry", method = POST)
    public ResponseEntity registry(@Valid @RequestBody RegistryUserForm regForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            new ResponseEntity(VALIDATION_ERROR_MESSAGE, BAD_REQUEST);
        }

        if (userRegistryFacade.isUserExists(regForm.getLogin())) {
            return new ResponseEntity(regForm.getLogin() + USER_ALREADY_EXSISTS_ERROR_MESSAGE, BAD_REQUEST);
        }
        userRegistryFacade.userReg(regForm);
        return new ResponseEntity(SUCCESS_FOR_USER + regForm.getLogin(), OK);
    }
}
