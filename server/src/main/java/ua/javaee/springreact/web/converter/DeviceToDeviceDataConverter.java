package ua.javaee.springreact.web.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.javaee.springreact.web.data.DeviceData;
import ua.javaee.springreact.web.entity.Device;

import static java.util.stream.Collectors.toList;

@Component
public class DeviceToDeviceDataConverter implements AbstractConverter<DeviceData, Device> {
    @Autowired
    private CommandToCommandDataConverter commandToCommandDataConverter;

    @Override
    public DeviceData convert(Device source) {
        DeviceData target = new DeviceData();
        target.setDeviceType(source.getDeviceType());
        target.setCommands(source.getCommands().stream().map(c -> commandToCommandDataConverter.convert(c)).collect(toList()));
        target.setName(source.getName());
        return target;
    }
}
