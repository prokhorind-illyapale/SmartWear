package ua.javaee.springreact.web.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table
public class Command {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "command_id")
    private long commandId;
    @Column
    private String name;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "Device_To_Command",
            joinColumns = {@JoinColumn(name = "command_id")},
            inverseJoinColumns = {@JoinColumn(name = "device_id")}
    )
    private List<Device> devices = new ArrayList<>();

    public long getCommandId() {
        return commandId;
    }

    public void setCommandId(long commandId) {
        this.commandId = commandId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Device> getDevices() {
        return devices;
    }

    public void setDevices(List<Device> devices) {
        this.devices = devices;
    }
}
