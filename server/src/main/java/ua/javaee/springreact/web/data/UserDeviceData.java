package ua.javaee.springreact.web.data;

public class UserDeviceData {

    private String user;
    private RoomData room;
    private DeviceData device;
    private String name;


    public void setUser(String user) {
        this.user = user;
    }

    public String getUser() {
        return user;
    }

    public RoomData getRoom() {
        return room;
    }

    public void setRoom(RoomData room) {
        this.room = room;
    }

    public DeviceData getDevice() {
        return device;
    }

    public void setDevice(DeviceData device) {
        this.device = device;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
