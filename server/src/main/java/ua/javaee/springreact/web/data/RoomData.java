package ua.javaee.springreact.web.data;

import javax.validation.constraints.NotNull;

public class RoomData {
    @NotNull
    private String roomName;

    public RoomData() {
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

}
