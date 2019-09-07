package ua.javaee.springreact.web.data;

import ua.javaee.springreact.web.entity.enums.DeviceType;

import java.util.List;

public class DeviceData {
    private String name;
    private DeviceType deviceType;
    private List<CommandData> commands;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DeviceType getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(DeviceType deviceType) {
        this.deviceType = deviceType;
    }

    public void setCommands(List<CommandData> commands) {
        this.commands = commands;
    }

    public List<CommandData> getCommands() {
        return commands;
    }
}
