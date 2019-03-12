package ua.javaee.springreact.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.javaee.springreact.web.converter.AbstractConverter;
import ua.javaee.springreact.web.data.LookData;
import ua.javaee.springreact.web.entity.Look;
import ua.javaee.springreact.web.facade.LookFacade;
import ua.javaee.springreact.web.facade.UserFacade;
import ua.javaee.springreact.web.form.lookforms.LookForm;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static ua.javaee.springreact.web.util.error.ErrorHelper.processingErrors;
import static ua.javaee.springreact.web.util.error.ErrorTypes.PERMISSION_TYPE_ERROR;
import static ua.javaee.springreact.web.util.error.ErrorTypes.VALIDATION_TYPE_ERROR;

/**
 * Created by kleba on 24.02.2019.
 */
@RestController
@RequestMapping(value = "/look")
public class LookController {

    private static final String NO_RIGHTS_FOR_THIS_ACTION = "No rights for this action:";
    private static final String CODE_NOT_FOUND = "Code not found:";
    private static final String USER_NOT_FOUND = "User not found for login:";

    @Autowired
    private LookFacade lookFacade;

    @Autowired
    private UserFacade userFacade;

    @Autowired
    @Qualifier("lookDataToFormConverter")
    private AbstractConverter lookDataToFormConverter;

    @RequestMapping(value = "/get/{code}", method = GET)
    public ResponseEntity<?> getLookByCode(@PathVariable("code") String code, Principal principal) {
        if (!lookFacade.isLookNumberExists(code)) {
            return processingErrors(CODE_NOT_FOUND + principal.getName(), VALIDATION_TYPE_ERROR);
        }

        if (isUserHasRights(code, principal)) {
            LookData lookData = lookFacade.findByCode(code);
            LookForm form = (LookForm) lookDataToFormConverter.convert(lookData);
            return new ResponseEntity<Object>(form, OK);
        } else {
            return processingErrors(NO_RIGHTS_FOR_THIS_ACTION + principal.getName(), PERMISSION_TYPE_ERROR);
        }
    }

    @RequestMapping(value = "/get/all/{login}", method = GET)
    public ResponseEntity<?> getLooksByLogin(@PathVariable("login") String login, Principal principal) {
        if (!userFacade.isUserExists(login)) {
            return processingErrors(USER_NOT_FOUND + login, VALIDATION_TYPE_ERROR);
        }
        if (principal.getName().equalsIgnoreCase(login) || userFacade.isUserHasAdminRights(principal.getName())) {
            List<LookData> looks = lookFacade.findAllUserLooks(login);
            List<LookForm> forms = new ArrayList<>();
            for (LookData lookData : looks) {
                LookForm form = (LookForm) lookDataToFormConverter.convert(lookData);
                forms.add(form);
            }
            return new ResponseEntity<Object>(forms, OK);
        } else {
            return processingErrors(NO_RIGHTS_FOR_THIS_ACTION + principal.getName(), PERMISSION_TYPE_ERROR);
        }

    }


    @RequestMapping(value = "/delete/{code}", method = DELETE)
    public ResponseEntity<?> deleteLookByCode(@PathVariable("code") String code, Principal principal) {
        if (!lookFacade.isLookNumberExists(code)) {

            return processingErrors(CODE_NOT_FOUND + principal.getName(), VALIDATION_TYPE_ERROR);
        }

        if (isUserHasRights(code, principal)) {
            Look look = lookFacade.findModelByCode(code);
            lookFacade.deleteLookByCode(look.getCode());
            return new ResponseEntity(OK);
        } else {
            return processingErrors(NO_RIGHTS_FOR_THIS_ACTION + principal.getName(), PERMISSION_TYPE_ERROR);
        }
    }


    private boolean isUserHasRights(String code, Principal principal) {
        return userFacade.isUserHasAdminRights(principal.getName()) || lookFacade.isPrincipalLook(code, principal.getName()) || lookFacade.isLookPublic(code);
    }
}