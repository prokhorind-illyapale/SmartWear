package ua.javaee.springreact.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.javaee.springreact.web.converter.UserDeviceDataFormToUserDeviceDataConverter;
import ua.javaee.springreact.web.data.UserDeviceData;
import ua.javaee.springreact.web.facade.UserFacade;
import ua.javaee.springreact.web.facade.impl.DefaultUserDeviceFacade;
import ua.javaee.springreact.web.form.UserDeviceDataForm;

import java.security.Principal;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static ua.javaee.springreact.web.util.error.ErrorHelper.processingErrors;
import static ua.javaee.springreact.web.util.error.ErrorTypes.PERMISSION_TYPE_ERROR;
import static ua.javaee.springreact.web.util.error.ErrorTypes.VALIDATION_TYPE_ERROR;

@RestController
@RequestMapping("/user-device")
public class UserDeviceController {

    public static final String NO_RIGHTS = "You don't have rights for this action";
    public static final String DEVICE_ALREADY_EXISTS = "Device already exists";
    public static final String DEVICE_NOT_EXISTS = "Device not exists";
    @Autowired
    private DefaultUserDeviceFacade defaultUserDeviceFacade;
    @Autowired
    private UserDeviceDataFormToUserDeviceDataConverter userDeviceDataFormToUserDeviceDataConverter;
    @Autowired
    private UserFacade userFacade;

    @GetMapping
    public ResponseEntity getDevices(Principal principal) {
        return ResponseEntity.ok(defaultUserDeviceFacade.findAllByLogin(principal.getName()));
    }

    @GetMapping("/{name}")
    public ResponseEntity getDevices(@PathVariable String name, Principal principal) {
        return ResponseEntity.ok(defaultUserDeviceFacade.find(principal.getName(), name));
    }

    @GetMapping("/{name}/room")
    public ResponseEntity getDevicesInRoom(@PathVariable String name, Principal principal) {
        return ResponseEntity.ok(defaultUserDeviceFacade.findAllByLoginAndRoomName(principal.getName(), name));
    }

    @GetMapping("/indicator/types")
    public ResponseEntity getIndicatorTypes(Principal principal) {
        return ResponseEntity.ok(defaultUserDeviceFacade.findDeviceTypes(principal.getName()));
    }

    @PostMapping
    public ResponseEntity addDevice(@RequestBody UserDeviceDataForm dataForm, Principal principal) {
        dataForm.setUser(principal.getName());

        if (nonNull(defaultUserDeviceFacade.find(principal.getName(), dataForm.getName()))) {
            return processingErrors(DEVICE_ALREADY_EXISTS, VALIDATION_TYPE_ERROR);
        }
        UserDeviceData deviceData = userDeviceDataFormToUserDeviceDataConverter.convert(dataForm);
        defaultUserDeviceFacade.add(deviceData);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{deviceName}")
    public ResponseEntity removeDevice(@PathVariable String deviceName, Principal principal) {
        if (!userFacade.isUserHasAdminRights(principal.getName())) {
            return processingErrors(NO_RIGHTS, PERMISSION_TYPE_ERROR);
        }
        UserDeviceData deviceData = defaultUserDeviceFacade.find(principal.getName(), deviceName);
        if (isNull(deviceData)) {
            return processingErrors(DEVICE_NOT_EXISTS, VALIDATION_TYPE_ERROR);
        }
        defaultUserDeviceFacade.remove(principal.getName(), deviceName);
        return ResponseEntity.ok().build();
    }
}
