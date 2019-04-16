package ua.javaee.springreact.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.javaee.springreact.web.converter.ClothTypeDataToFormConverter;
import ua.javaee.springreact.web.data.ClothTypeData;
import ua.javaee.springreact.web.facade.ClothTypeFacade;
import ua.javaee.springreact.web.facade.UserFacade;
import ua.javaee.springreact.web.form.lookforms.ClothTypeDataForm;

import java.security.Principal;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toList;
import static org.springframework.http.ResponseEntity.ok;
import static ua.javaee.springreact.web.util.controller.ControllerConstants.NO_RIGHTS_FOR_THIS_ACTION;
import static ua.javaee.springreact.web.util.error.ErrorHelper.processingErrors;
import static ua.javaee.springreact.web.util.error.ErrorTypes.PERMISSION_TYPE_ERROR;
import static ua.javaee.springreact.web.util.error.ErrorTypes.VALIDATION_TYPE_ERROR;

/**
 * Created by kleba on 24.02.2019.
 */
@RestController
@RequestMapping(value = "/cloth/type")
public class ClothTypeController {

    private static final String CLOTH_TYPE_ALREADY_EXISTS = "Cloth type already exists";
    private static final String CLOTH_TYPE_NOT_EXISTS = "Cloth type not exists";
    @Autowired
    private ClothTypeFacade clothTypeFacade;
    @Autowired
    private ClothTypeDataToFormConverter converter;
    @Autowired
    private UserFacade userFacade;

    @GetMapping
    public ResponseEntity<List<ClothTypeDataForm>> getClothTypes(Principal principal) {
        List<ClothTypeData> clothTypeDatas = clothTypeFacade.getAllClothTypes();
        return ok(clothTypeDatas.stream().map(this::convert).collect(toList()));
    }

    @GetMapping("/{name}")
    public ResponseEntity getClothType(Principal principal, @PathVariable String name) {
        if (isNull(clothTypeFacade.getClothTypeData(name))) {
            return processingErrors(CLOTH_TYPE_NOT_EXISTS, VALIDATION_TYPE_ERROR);
        }
        return ok(clothTypeFacade.getClothTypeData(name));
    }

    @PostMapping
    public ResponseEntity saveClothType(ClothTypeDataForm form, Principal principal) {
        if (!userFacade.isUserHasAdminRights(principal.getName())) {
            return processingErrors(NO_RIGHTS_FOR_THIS_ACTION + principal.getName(), PERMISSION_TYPE_ERROR);
        }
        if (nonNull(clothTypeFacade.getClothTypeData(form.getName()))) {
            return processingErrors(CLOTH_TYPE_ALREADY_EXISTS, VALIDATION_TYPE_ERROR);
        }
        clothTypeFacade.saveClothType(form.getName());
        return ok().build();
    }


    @DeleteMapping("/{name}")
    public ResponseEntity removeClothType(Principal principal, @PathVariable String name) {
        if (!userFacade.isUserHasAdminRights(principal.getName())) {
            return processingErrors(NO_RIGHTS_FOR_THIS_ACTION + principal.getName(), PERMISSION_TYPE_ERROR);
        }

        if (isNull(clothTypeFacade.getClothTypeData(name))) {
            return processingErrors(CLOTH_TYPE_NOT_EXISTS, VALIDATION_TYPE_ERROR);
        }
        clothTypeFacade.removeClothType(name);
        return ok().build();
    }

    private ClothTypeDataForm convert(ClothTypeData clothType) {
        return converter.convert(clothType);
    }
}
