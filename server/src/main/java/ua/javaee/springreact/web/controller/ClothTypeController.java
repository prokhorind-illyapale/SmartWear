package ua.javaee.springreact.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.javaee.springreact.web.converter.ClothTypeDataToFormConverter;
import ua.javaee.springreact.web.data.ClothTypeData;
import ua.javaee.springreact.web.facade.ClothTypeFacade;
import ua.javaee.springreact.web.form.lookforms.ClothTypeDataForm;

import java.security.Principal;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.ResponseEntity.ok;

/**
 * Created by kleba on 24.02.2019.
 */
@RestController
@RequestMapping(value = "/cloth/type")
public class ClothTypeController {

    @Autowired
    private ClothTypeFacade clothTypeFacade;
    @Autowired
    private ClothTypeDataToFormConverter converter;

    @GetMapping
    public ResponseEntity<List<ClothTypeDataForm>> getClothTypes(Principal principal) {
        List<ClothTypeData> clothTypeDatas = clothTypeFacade.getAllClothTypes();
        return ok(clothTypeDatas.stream().map(this::convert).collect(toList()));
    }

    @PostMapping
    public ResponseEntity<?> saveClothType(ClothTypeDataForm form, Principal principal) {
        return ok().build();
    }

    @PutMapping
    public ResponseEntity<?> updateClothType(Principal principal) {

        return ok().build();
    }

    @DeleteMapping
    public ResponseEntity removeClothType(Principal principal) {

        return ok().build();
    }

    private ClothTypeDataForm convert(ClothTypeData clothType) {
        return converter.convert(clothType);
    }
}
