package ua.javaee.springreact.web.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.javaee.springreact.web.data.CommandData;
import ua.javaee.springreact.web.data.DeviceData;
import ua.javaee.springreact.web.entity.Command;
import ua.javaee.springreact.web.entity.Device;
import ua.javaee.springreact.web.service.impl.DefaultCommandService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.util.Collections.emptyList;

@Component
public class DeviceDataToDeviceConverter implements AbstractConverter<Device, DeviceData> {
    @Autowired
    private DefaultCommandService defaultCommandService;

    @Override
    public Device convert(DeviceData source) {
        Device target = new Device();
        target.setName(source.getName());
        target.setDeviceType(source.getDeviceType());
        target.setCommands(findCommands(source.getCommands()));
        target.setUserDevices(emptyList());
        return target;
    }

    private List<Command> findCommands(List<CommandData> commandData) {
        List<Command> commands = new ArrayList<>();
        for (CommandData c : commandData) {
            Command command = defaultCommandService.findByName(c.getName());
            if (Objects.nonNull(command)) {
                commands.add(command);
            }
        }
        return commands;
    }
}
