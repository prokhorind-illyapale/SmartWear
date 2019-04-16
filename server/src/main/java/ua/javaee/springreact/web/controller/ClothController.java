package ua.javaee.springreact.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.javaee.springreact.web.data.ClothData;
import ua.javaee.springreact.web.facade.ClothFacade;

import java.security.Principal;

import static org.springframework.http.ResponseEntity.ok;

/**
 * Created by kleba on 16.04.2019.
 */
@RestController
@RequestMapping(value = "/cloth")
public class ClothController {

    @Autowired
    private ClothFacade clothFacade;

    @GetMapping
    public ResponseEntity getClothes(Principal principal) {
        return ok().body(clothFacade.getAllClothes());
    }

    public ResponseEntity saveCloth(Principal principal, @RequestBody ClothData clothData) {
        return ok().build();
    }
}
