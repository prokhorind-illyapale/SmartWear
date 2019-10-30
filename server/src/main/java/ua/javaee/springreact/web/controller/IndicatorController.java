package ua.javaee.springreact.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.javaee.springreact.web.facade.impl.DefaultIndicatorFacade;
import ua.javaee.springreact.web.facade.impl.DefaultRoomFacade;

import java.security.Principal;

import static java.util.Objects.isNull;
import static ua.javaee.springreact.web.util.error.ErrorHelper.processingErrors;
import static ua.javaee.springreact.web.util.error.ErrorTypes.PERMISSION_TYPE_ERROR;

@RestController
@RequestMapping("/indicator")
public class IndicatorController {

    public static final String NO_RIGHTS = "You have no rights for this action";
    @Autowired
    private DefaultRoomFacade defaultRoomFacade;
    @Autowired
    private DefaultIndicatorFacade defaultIndicatorFacade;

    @GetMapping("/room/{roomName}")
    public ResponseEntity getLastValuesInRoom(@PathVariable String roomName, Principal principal) {
        if (isNull(defaultRoomFacade.find(roomName, principal.getName()))) {
            return processingErrors(NO_RIGHTS, PERMISSION_TYPE_ERROR);
        }
        return ResponseEntity.ok(defaultIndicatorFacade.getLastValuesInRoom(principal.getName(), roomName));
    }

    @GetMapping
    public ResponseEntity getLastValueByDeviceName(@RequestParam String deviceName, Principal principal) {
        return ResponseEntity.ok(defaultIndicatorFacade.getLastValue(principal.getName(), deviceName));
    }
}
