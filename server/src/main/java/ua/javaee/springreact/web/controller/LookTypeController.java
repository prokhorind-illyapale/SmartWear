package ua.javaee.springreact.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.javaee.springreact.web.data.LookTypeData;
import ua.javaee.springreact.web.facade.LookTypeFacade;
import ua.javaee.springreact.web.facade.UserFacade;

import java.security.Principal;

import static org.springframework.http.ResponseEntity.ok;
import static ua.javaee.springreact.web.util.controller.ControllerConstants.NO_RIGHTS_FOR_THIS_ACTION;
import static ua.javaee.springreact.web.util.error.ErrorHelper.processingErrors;
import static ua.javaee.springreact.web.util.error.ErrorTypes.PERMISSION_TYPE_ERROR;
import static ua.javaee.springreact.web.util.error.ErrorTypes.VALIDATION_TYPE_ERROR;

@RestController
@RequestMapping(value = "/look-type")
public class LookTypeController {

    public static final String LOOK_TYPE_NOT_EXISTS = "Look type not exists";
    public static final String LOOK_TYPE_ALREADY_EXISTS = "Look type already exists";
    @Autowired
    private LookTypeFacade lookTypeFacade;

    @Autowired
    private UserFacade userFacade;

    @GetMapping
    public ResponseEntity getAllLookTypes() {
        return ok(lookTypeFacade.getAllLookTypes());
    }

    @DeleteMapping("/{name}")
    public ResponseEntity delete(@PathVariable String name, Principal principal) {
        if (!userFacade.isUserHasAdminRights(principal.getName())) {
            return processingErrors(NO_RIGHTS_FOR_THIS_ACTION + principal.getName(), PERMISSION_TYPE_ERROR);
        }
        if (!lookTypeFacade.isLookTypeExists(name)) {
            return processingErrors(LOOK_TYPE_NOT_EXISTS, PERMISSION_TYPE_ERROR);
        }
        lookTypeFacade.removeLookType(name);
        return ok().build();
    }

    @PostMapping
    public ResponseEntity save(@RequestBody LookTypeData lookTypeData, Principal principal) {
        if (!userFacade.isUserHasAdminRights(principal.getName())) {
            return processingErrors(NO_RIGHTS_FOR_THIS_ACTION + principal.getName(), PERMISSION_TYPE_ERROR);
        }
        if (lookTypeFacade.isLookTypeExists(lookTypeData.getName())) {
            return processingErrors(LOOK_TYPE_ALREADY_EXISTS, VALIDATION_TYPE_ERROR);
        }
        lookTypeFacade.saveLookType(lookTypeData);
        return ok().build();
    }

}
