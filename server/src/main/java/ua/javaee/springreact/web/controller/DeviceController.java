package ua.javaee.springreact.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.javaee.springreact.web.data.DeviceData;
import ua.javaee.springreact.web.facade.UserFacade;
import ua.javaee.springreact.web.facade.impl.DefaultDeviceFacade;

import java.security.Principal;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static ua.javaee.springreact.web.util.error.ErrorHelper.processingErrors;
import static ua.javaee.springreact.web.util.error.ErrorTypes.PERMISSION_TYPE_ERROR;
import static ua.javaee.springreact.web.util.error.ErrorTypes.VALIDATION_TYPE_ERROR;

@RestController
@RequestMapping("/device")
public class DeviceController {

    public static final String NO_RIGHTS = "You don't have rights for this action";
    public static final String DEVICE_ALREADY_EXISTS = "Device already exists";
    public static final String DEVICE_NOT_EXISTS = "Device not exists";
    @Autowired
    private DefaultDeviceFacade defaultDeviceFacade;
    @Autowired
    private UserFacade userFacade;

    @GetMapping
    public ResponseEntity getDevices(Principal principal) {
        if (!userFacade.isUserHasAdminRights(principal.getName())) {
            return processingErrors(NO_RIGHTS, PERMISSION_TYPE_ERROR);
        }
        return ResponseEntity.ok(defaultDeviceFacade.findAll());
    }

    @PostMapping
    public ResponseEntity addDevice(@RequestBody DeviceData deviceData, Principal principal) {
        if (!userFacade.isUserHasAdminRights(principal.getName())) {
            return processingErrors(NO_RIGHTS, PERMISSION_TYPE_ERROR);
        }

        if (nonNull(defaultDeviceFacade.findByName(deviceData.getName()))) {
            return processingErrors(DEVICE_ALREADY_EXISTS, VALIDATION_TYPE_ERROR);
        }
        defaultDeviceFacade.add(deviceData);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{deviceId}")
    public ResponseEntity removeDevice(@PathVariable String deviceId, Principal principal) {
        if (!userFacade.isUserHasAdminRights(principal.getName())) {
            return processingErrors(NO_RIGHTS, PERMISSION_TYPE_ERROR);
        }
        DeviceData deviceData = defaultDeviceFacade.findByName(deviceId);
        if (isNull(deviceData)) {
            return processingErrors(DEVICE_NOT_EXISTS, VALIDATION_TYPE_ERROR);
        }
        defaultDeviceFacade.remove(deviceData);
        return ResponseEntity.ok().build();
    }
}
