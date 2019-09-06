package ua.javaee.springreact.web.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.javaee.springreact.web.data.RoomData;
import ua.javaee.springreact.web.entity.Room;
import ua.javaee.springreact.web.service.UserService;

@Component
public class RoomDataToRoomConverter implements AbstractConverter<Room, RoomData> {

    @Autowired
    private UserService userService;

    @Override
    public Room convert(RoomData source) {
        Room target = new Room();
        target.setRoomName(source.getRoomName());
        return target;
    }
}
