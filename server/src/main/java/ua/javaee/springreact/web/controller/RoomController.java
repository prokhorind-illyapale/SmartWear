package ua.javaee.springreact.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.javaee.springreact.web.data.RoomData;
import ua.javaee.springreact.web.facade.impl.DefaultRoomFacade;

import java.security.Principal;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.springframework.http.ResponseEntity.ok;
import static ua.javaee.springreact.web.util.error.ErrorHelper.processingErrors;
import static ua.javaee.springreact.web.util.error.ErrorTypes.PERMISSION_TYPE_ERROR;

@RestController
@RequestMapping(value = "/room")
public class RoomController {

    @Autowired
    private DefaultRoomFacade defaultRoomFacade;

    @GetMapping("/user")
    public ResponseEntity findAll(Principal principal) {
        return ok(defaultRoomFacade.findByUserLogin(principal.getName()));
    }

    @PostMapping
    public ResponseEntity save(@RequestBody RoomData roomData, Principal principal) {
        if (nonNull(defaultRoomFacade.find(roomData.getRoomName(), principal.getName()))) {
            return processingErrors("Room with name " + roomData.getRoomName() + " already exists", PERMISSION_TYPE_ERROR);
        }
        defaultRoomFacade.save(roomData, principal.getName());
        return ok().build();
    }

    @DeleteMapping
    public ResponseEntity remove(@RequestBody RoomData roomData, Principal principal) {
        if (isNull(defaultRoomFacade.find(roomData.getRoomName(), principal.getName()))) {
            return processingErrors("Room with name " + roomData.getRoomName() + " doesn't exist", PERMISSION_TYPE_ERROR);
        }
        defaultRoomFacade.remove(roomData.getRoomName(), principal.getName());
        return ok().build();
    }
}
