package ua.javaee.springreact.web.facade.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.javaee.springreact.web.converter.RoomToRoomDataConverter;
import ua.javaee.springreact.web.data.RoomData;
import ua.javaee.springreact.web.entity.Room;
import ua.javaee.springreact.web.service.impl.DefaultRoomService;

import java.util.List;

import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toList;

@Component
public class DefaultRoomFacade {
    @Autowired
    private DefaultRoomService defaultRoomService;
    @Autowired
    private RoomToRoomDataConverter roomToRoomDataConverter;

    public RoomData find(String name, String user) {
        Room room = defaultRoomService.find(name, user);
        if (isNull(room)) {
            return null;
        }
        RoomData roomData = roomToRoomDataConverter.convert(room);
        return roomData;
    }

    public void remove(String name, String user) {
        defaultRoomService.remove(name, user);
    }

    public List<RoomData> findByUserLogin(String login) {
        List<Room> rooms = defaultRoomService.findByLogin(login);
        return rooms.stream().map(r -> roomToRoomDataConverter.convert(r)).collect(toList());
    }

    public void save(RoomData roomData, String user) {
        defaultRoomService.save(roomData, user);
    }
}
