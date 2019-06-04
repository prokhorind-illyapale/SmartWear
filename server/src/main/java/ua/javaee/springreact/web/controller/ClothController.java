package ua.javaee.springreact.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.javaee.springreact.web.data.ClothData;
import ua.javaee.springreact.web.facade.ClothFacade;
import ua.javaee.springreact.web.facade.UserFacade;

import java.security.Principal;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.springframework.http.ResponseEntity.ok;
import static ua.javaee.springreact.web.util.controller.ControllerConstants.NO_RIGHTS_FOR_THIS_ACTION;
import static ua.javaee.springreact.web.util.error.ErrorHelper.processingErrors;
import static ua.javaee.springreact.web.util.error.ErrorTypes.PERMISSION_TYPE_ERROR;
import static ua.javaee.springreact.web.util.error.ErrorTypes.VALIDATION_TYPE_ERROR;

/**
 * Created by kleba on 16.04.2019.
 */
@RestController
@RequestMapping(value = "/cloth")
public class ClothController {

    private static final String CLOTH_NOT_EXISTS = "Cloth not exists";
    private static final String CLOTH_ALREADY_EXISTS = "Cloth already exists";
    @Autowired
    private ClothFacade clothFacade;
    @Autowired
    private UserFacade userFacade;

    @GetMapping
    public ResponseEntity getClothes(Principal principal) {
        return ok().body(clothFacade.getAllClothes());
    }

    @GetMapping("/{clothName}/{sexType}")
    public ResponseEntity getCloth(@PathVariable String clothName, @PathVariable String sexType, Principal principal) {
        if (!userFacade.isUserHasAdminRights(principal.getName())) {
            return processingErrors(NO_RIGHTS_FOR_THIS_ACTION + principal.getName(), PERMISSION_TYPE_ERROR);
        }
        ClothData clothData = clothFacade.getCloth(clothName, sexType);
        if (isNull(clothData)) {
            return processingErrors(CLOTH_NOT_EXISTS, VALIDATION_TYPE_ERROR);
        }
        return ok().body(clothFacade.getCloth(clothName, sexType));
    }

    @DeleteMapping("/{clothName}/{sexType}")
    public ResponseEntity deleteCloth(@PathVariable String clothName, @PathVariable String sexType, Principal principal) {
        if (!userFacade.isUserHasAdminRights(principal.getName())) {
            return processingErrors(NO_RIGHTS_FOR_THIS_ACTION + principal.getName(), PERMISSION_TYPE_ERROR);
        }
        ClothData clothData = clothFacade.getCloth(clothName, sexType);
        if (isNull(clothData)) {
            return processingErrors(CLOTH_NOT_EXISTS, VALIDATION_TYPE_ERROR);
        }
        clothFacade.deleteCloth(clothName, sexType);
        return ok().build();
    }

    @PostMapping
    public ResponseEntity saveCloth(Principal principal, @RequestBody ClothData clothData) {
        if (!userFacade.isUserHasAdminRights(principal.getName())) {
            return processingErrors(NO_RIGHTS_FOR_THIS_ACTION + principal.getName(), PERMISSION_TYPE_ERROR);
        }

        if (nonNull(clothFacade.getCloth(clothData.getName(), clothData.getSex().getName()))) {
            return processingErrors(CLOTH_ALREADY_EXISTS, VALIDATION_TYPE_ERROR);
        }
        clothFacade.saveCloth(clothData);
        return ok().build();
    }
}
