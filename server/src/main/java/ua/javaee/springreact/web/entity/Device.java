package ua.javaee.springreact.web.entity;

import ua.javaee.springreact.web.entity.enums.DeviceType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.REMOVE;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table
public class Device {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "device_id")
    private long deviceId;
    @Column
    private String name;
    @Enumerated(EnumType.STRING)
    private DeviceType deviceType;
    @OneToMany(mappedBy = "device", cascade = REMOVE)
    private List<UserDevice> userDevices = new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.PERSIST})
    @JoinTable(
            name = "Device_To_Command",
            joinColumns = {@JoinColumn(name = "device_id")},
            inverseJoinColumns = {@JoinColumn(name = "command_id")}
    )
    private List<Command> commands = new ArrayList<>();

    public Device() {
    }

    public long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(long deviceId) {
        this.deviceId = deviceId;
    }

    public void setDeviceType(DeviceType deviceType) {
        this.deviceType = deviceType;
    }

    public List<UserDevice> getUserDevices() {
        return userDevices;
    }

    public void setCommands(List<Command> commands) {
        this.commands = commands;
    }

    public DeviceType getDeviceType() {
        return deviceType;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUserDevices(List<UserDevice> userDevices) {
        this.userDevices = userDevices;
    }

    public List<Command> getCommands() {
        return commands;
    }

    public String getName() {
        return name;
    }
}

