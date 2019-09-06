package ua.javaee.springreact.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.javaee.springreact.web.converter.RoomDataToRoomConverter;
import ua.javaee.springreact.web.data.RoomData;
import ua.javaee.springreact.web.entity.Room;
import ua.javaee.springreact.web.repository.RoomRepository;
import ua.javaee.springreact.web.service.UserService;

import java.util.Collections;
import java.util.List;

@Service
public class DefaultRoomService {
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private RoomDataToRoomConverter roomDataToRoomConverter;

    public Room find(String name, String user) {
        return roomRepository.findByRoomName(name, user);
    }

    public void remove(String name, String user) {
        Room room = roomRepository.findByRoomName(name, user);
        roomRepository.delete(room);
    }

    public List<Room> findByLogin(String login) {
        return roomRepository.findByLogin(login);
    }

    public void save(RoomData roomData, String user) {
        Room room = new Room();
        room.setRoomName(roomData.getRoomName());
        room.setUser(userService.getUserByLogin(user));
        room.setUserDevices(Collections.emptyList());
        roomRepository.save(room);
    }
}
