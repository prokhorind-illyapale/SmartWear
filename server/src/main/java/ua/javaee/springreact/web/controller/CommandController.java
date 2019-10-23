package ua.javaee.springreact.web.controller;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.javaee.springreact.config.RabbitMqPublisher;
import ua.javaee.springreact.web.data.CommandData;
import ua.javaee.springreact.web.entity.UserDevice;
import ua.javaee.springreact.web.facade.UserFacade;
import ua.javaee.springreact.web.facade.impl.DefaultCommandFacade;
import ua.javaee.springreact.web.facade.impl.DefaultDeviceFacade;
import ua.javaee.springreact.web.facade.impl.DefaultUserDeviceFacade;

import java.security.Principal;
import java.util.Objects;

import static java.lang.String.format;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.springframework.http.ResponseEntity.ok;
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
    public static final String ESP_COMMAND_TOPIC = "esp/command";
    @Autowired
    private DefaultCommandFacade defaultCommandFacade;
    @Autowired
    private DefaultDeviceFacade defaultDeviceFacade;
    @Autowired
    private DefaultUserDeviceFacade defaultUserDeviceFacade;
    @Autowired
    private UserFacade userFacade;
    @Autowired
    private RabbitMqPublisher rabbitMqPublisher;

    @GetMapping
    public ResponseEntity getCommands(Principal principal) {
        if (!userFacade.isUserHasAdminRights(principal.getName())) {
            return processingErrors(NO_RIGHTS, PERMISSION_TYPE_ERROR);
        }
        return ok(defaultCommandFacade.findAll());
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
        return ok().build();
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
        return ok().build();
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
        return ok().build();
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
        return ok().build();
    }

    @PostMapping("/{command}/{userDeviceName}")
    public ResponseEntity doCommand(@PathVariable String command, @PathVariable String userDeviceName, Principal principal) throws MqttException {
        UserDevice userDeviceData = defaultUserDeviceFacade.findModel(principal.getName(), userDeviceName);

        if (Objects.isNull(userDeviceData)) {
            return processingErrors(NO_RIGHTS, PERMISSION_TYPE_ERROR);
        }
        rabbitMqPublisher.send(format("%s/%s", ESP_COMMAND_TOPIC, userDeviceData.getUserDeviceId()), command, userDeviceData.getPin());
        defaultCommandFacade.saveCommandRecord(command, principal.getName(), userDeviceName);
        return ok().build();
    }
}
