package ua.javaee.springreact.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.javaee.springreact.web.data.CommandData;
import ua.javaee.springreact.web.facade.UserFacade;
import ua.javaee.springreact.web.facade.impl.DefaultCommandFacade;
import ua.javaee.springreact.web.facade.impl.DefaultDeviceFacade;

import java.security.Principal;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static ua.javaee.springreact.web.util.error.ErrorHelper.processingErrors;
import static ua.javaee.springreact.web.util.error.ErrorTypes.PERMISSION_TYPE_ERROR;
import static ua.javaee.springreact.web.util.error.ErrorTypes.VALIDATION_TYPE_ERROR;

@RestController
@RequestMapping("/command")
public class CommandController {

    public static final String NO_RIGHTS = "You don't have rights for this action";
    public static final String COMMAND_ALREADY_EXISTS = "Command already exists";
    public static final String COMMAND_NOT_EXISTS = "Command not exists";
    public static final String DEVICE_CONTAINS_COMMAND = "Device already contains this command";
    public static final String DEVICE_NO_COMMAND = "Device doesn't contains command";
    @Autowired
    private DefaultCommandFacade defaultCommandFacade;
    @Autowired
    private DefaultDeviceFacade defaultDeviceFacade;
    @Autowired
    private UserFacade userFacade;

    @GetMapping
    public ResponseEntity getCommands(Principal principal) {
        if (!userFacade.isUserHasAdminRights(principal.getName())) {
            return processingErrors(NO_RIGHTS, PERMISSION_TYPE_ERROR);
        }
        return ResponseEntity.ok(defaultCommandFacade.findAll());
    }

    @PostMapping
    public ResponseEntity addCommand(@RequestBody CommandData command, Principal principal) {
        if (!userFacade.isUserHasAdminRights(principal.getName())) {
            return processingErrors(NO_RIGHTS, PERMISSION_TYPE_ERROR);
        }

        if (nonNull(defaultCommandFacade.findByName(command.getName()))) {
            return processingErrors(COMMAND_ALREADY_EXISTS, VALIDATION_TYPE_ERROR);
        }
        defaultCommandFacade.add(command);
        return ResponseEntity.ok().build();
    }

    @PostMapping("{commandName}/device/{deviceName}")
    public ResponseEntity addCommandToDevice(@PathVariable String commandName, @PathVariable String deviceName, Principal principal) {
        if (!userFacade.isUserHasAdminRights(principal.getName())) {
            return processingErrors(NO_RIGHTS, PERMISSION_TYPE_ERROR);
        }

        if (defaultDeviceFacade.isDeviceContainsCommand(commandName, deviceName)) {
            return processingErrors(DEVICE_CONTAINS_COMMAND, VALIDATION_TYPE_ERROR);
        }
        defaultDeviceFacade.addCommand(commandName, deviceName);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{commandName}/device/{deviceName}")
    public ResponseEntity deleteCommandFromDevice(@PathVariable String commandName, @PathVariable String deviceName, Principal principal) {
        if (!userFacade.isUserHasAdminRights(principal.getName())) {
            return processingErrors(NO_RIGHTS, PERMISSION_TYPE_ERROR);
        }

        if (!defaultDeviceFacade.isDeviceContainsCommand(commandName, deviceName)) {
            return processingErrors(DEVICE_NO_COMMAND, VALIDATION_TYPE_ERROR);
        }
        defaultDeviceFacade.deleteCommand(commandName, deviceName);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity removeCommand(@RequestBody CommandData command, Principal principal) {
        if (!userFacade.isUserHasAdminRights(principal.getName())) {
            return processingErrors(NO_RIGHTS, PERMISSION_TYPE_ERROR);
        }
        if (isNull(defaultCommandFacade.findByName(command.getName()))) {
            return processingErrors(COMMAND_NOT_EXISTS, VALIDATION_TYPE_ERROR);
        }
        defaultCommandFacade.remove(command);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{command}/{userDeviceName}")
    public ResponseEntity doCommand(@PathVariable String command, @PathVariable String userDeviceName, Principal principal) {
        return ResponseEntity.ok().build();
    }
}
