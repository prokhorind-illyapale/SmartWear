package ua.javaee.springreact.web.facade.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.javaee.springreact.web.converter.DeviceToDeviceDataConverter;
import ua.javaee.springreact.web.data.DeviceData;
import ua.javaee.springreact.web.entity.Device;
import ua.javaee.springreact.web.service.impl.DefaultDeviceService;

import java.util.List;
import java.util.Objects;

import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toList;

@Component
public class DefaultDeviceFacade {
    @Autowired
    private DefaultDeviceService defaultDeviceService;
    @Autowired
    private DeviceToDeviceDataConverter deviceToDeviceDataConverter;

    public void add(DeviceData deviceData) {
        defaultDeviceService.add(deviceData);
    }

    public void addCommand(String commandName, String deviceName) {
        defaultDeviceService.addCommandToDevice(commandName, deviceName);
    }

    public void deleteCommand(String commandName, String deviceName) {
        defaultDeviceService.deleteCommandFromDevice(commandName, deviceName);
    }

    public boolean isDeviceContainsCommand(String commandName, String deviceName) {
        Device device = defaultDeviceService.findByName(deviceName);
        if (Objects.isNull(device)) {
            return false;
        }
        return device.getCommands().stream().anyMatch(c -> commandName.equalsIgnoreCase(c.getName()));
    }

    public void remove(DeviceData deviceData) {
        defaultDeviceService.remove(deviceData);
    }

    public DeviceData findByName(String name) {
        Device device = defaultDeviceService.findByName(name);
        return isNull(device) ? null : deviceToDeviceDataConverter.convert(device);
    }

    public List<DeviceData> findAll() {
        return defaultDeviceService.findAll().stream().map(c -> deviceToDeviceDataConverter.convert(c)).collect(toList());
    }
}
