package ua.javaee.springreact.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.javaee.springreact.web.converter.AbstractConverter;
import ua.javaee.springreact.web.data.LookData;
import ua.javaee.springreact.web.facade.LookFacade;
import ua.javaee.springreact.web.facade.UserFacade;
import ua.javaee.springreact.web.form.lookforms.LookForm;

import java.security.Principal;

import static org.springframework.http.HttpStatus.OK;
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


    private boolean isUserHasRights(String code, Principal principal) {
        return userFacade.isUserHasAdminRights(principal.getName()) || lookFacade.isPrincipalLook(code, principal.getName()) || lookFacade.isLookPublic(code);
    }
}
