package ua.javaee.springreact.web.entity;

import javax.persistence.*;

import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table
public class UserDevice {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "user_device_id")
    private long userDeviceId;
    @JoinColumn(name = "user_id")
    @ManyToOne(cascade = PERSIST)
    private User user;
    @JoinColumn(name = "room_id")
    @ManyToOne(cascade = PERSIST, optional = true, fetch = FetchType.LAZY)
    private Room room;
    @JoinColumn(name = "device_id")
    @ManyToOne
    private Device device;
    @Column
    private String valueType;
    @Column
    private String name;
    @Column
    private int pin;

    public UserDevice() {
    }

    public long getUserDeviceId() {
        return userDeviceId;
    }

    public void setUserDeviceId(long userDeviceId) {
        this.userDeviceId = userDeviceId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    public int getPin() {
        return pin;
    }

    public void setValueType(String valueType) {
        this.valueType = valueType;
    }

    public String getValueType() {
        return valueType;
    }
}

