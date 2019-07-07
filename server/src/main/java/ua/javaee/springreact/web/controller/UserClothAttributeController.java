package ua.javaee.springreact.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.javaee.springreact.web.data.UserClothAttributeData;
import ua.javaee.springreact.web.facade.ClothFacade;
import ua.javaee.springreact.web.facade.UserClothAttributeFacade;
import ua.javaee.springreact.web.facade.UserFacade;

import java.security.Principal;

import static java.util.Objects.isNull;
import static org.springframework.http.ResponseEntity.ok;
import static ua.javaee.springreact.web.util.error.ErrorHelper.processingErrors;
import static ua.javaee.springreact.web.util.error.ErrorTypes.PERMISSION_TYPE_ERROR;
import static ua.javaee.springreact.web.util.error.ErrorTypes.VALIDATION_TYPE_ERROR;

@RestController
@RequestMapping(value = "/user-cloth")
public class UserClothAttributeController {

    public static final String NOT_EXISTS_WITH_CODE = "User cloth attributes  is not exists with code:";
    public static final String NO_PERMISSIONS_FOR_THIS_ACTION = "You have no permissions for this action";
    public static final String CANT_SAVE = "Cant'save";
    public static final String USER_CLOTH_ATTRIBUTE_WAS_NOT_SAVED = "user cloth attribute was not saved";
    @Autowired
    private UserClothAttributeFacade userClothAttributeFacade;
    @Autowired
    private ClothFacade clothFacade;
    @Autowired
    private UserFacade userFacade;

    @GetMapping("/{code}")
    public ResponseEntity get(@PathVariable Long code, Principal principal) {
        if (!userFacade.isUserHasAdminRights(principal.getName())
                && !userClothAttributeFacade.isUserClothAttributes(userFacade.getUserByLogin(principal.getName()), code)) {
            return processingErrors(NO_PERMISSIONS_FOR_THIS_ACTION, PERMISSION_TYPE_ERROR);
        }
        UserClothAttributeData data = userClothAttributeFacade.get(code);
        if (isNull(data)) {
            return processingErrors(NOT_EXISTS_WITH_CODE + code, VALIDATION_TYPE_ERROR);
        }
        return ok(data);
    }

    @GetMapping("/{userName}/{pageNumber}")
    public ResponseEntity getAllAttributesByUserName(@PathVariable String userName, @PathVariable int pageNumber,
                                                     @RequestParam(required = false, defaultValue = "5") int size,
                                                     Principal principal
    ) {
        if (!userFacade.isUserHasAdminRights(principal.getName())
                && !principal.getName().equalsIgnoreCase(userName)) {
            return processingErrors(NO_PERMISSIONS_FOR_THIS_ACTION, PERMISSION_TYPE_ERROR);
        }
        return ok(userClothAttributeFacade.get(userName, pageNumber, size));
    }

    @DeleteMapping("/{code}")
    public ResponseEntity remove(@PathVariable Long code, Principal principal) {
        if (!userFacade.isUserHasAdminRights(principal.getName())
                && !userClothAttributeFacade.isUserClothAttributes(userFacade.getUserByLogin(principal.getName()), code)) {
            return processingErrors(NO_PERMISSIONS_FOR_THIS_ACTION, PERMISSION_TYPE_ERROR);
        }
        if (isNull(userClothAttributeFacade.get(code))) {
            return processingErrors(NOT_EXISTS_WITH_CODE + code, VALIDATION_TYPE_ERROR);
        }
        userClothAttributeFacade.remove(code);
        return ok().build();
    }

    @PutMapping("/{code}")
    public ResponseEntity update(@PathVariable Long code, Principal principal, @RequestBody UserClothAttributeData userClothAttributeData) {
        if (!userFacade.isUserHasAdminRights(principal.getName())
                && !userClothAttributeFacade.isUserClothAttributes(userFacade.getUserByLogin(principal.getName()), code)) {
            return processingErrors(NO_PERMISSIONS_FOR_THIS_ACTION, PERMISSION_TYPE_ERROR);
        }

        if (userClothAttributeFacade.update(userClothAttributeData, code)) {
            return ok().build();
        }
        return processingErrors(CANT_SAVE, VALIDATION_TYPE_ERROR);
    }

    @PostMapping
    public ResponseEntity save(@RequestBody UserClothAttributeData userClothAttributeData) {
        long saved = userClothAttributeFacade.save(userClothAttributeData);
        if (saved == 0l) {
            return processingErrors(USER_CLOTH_ATTRIBUTE_WAS_NOT_SAVED, VALIDATION_TYPE_ERROR);
        }
        return ok(saved);
    }
}
