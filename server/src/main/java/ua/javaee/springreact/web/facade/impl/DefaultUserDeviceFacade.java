package ua.javaee.springreact.web.facade.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.javaee.springreact.web.converter.UserDeviceToUserDeviceDataConverter;
import ua.javaee.springreact.web.data.UserDeviceData;
import ua.javaee.springreact.web.entity.Room;
import ua.javaee.springreact.web.entity.UserDevice;
import ua.javaee.springreact.web.service.impl.DefaultRoomService;
import ua.javaee.springreact.web.service.impl.DefaultUserDeviceService;

import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toList;

@Component
public class DefaultUserDeviceFacade {
    @Autowired
    private DefaultUserDeviceService defaultUserDeviceService;
    @Autowired
    private DefaultRoomService defaultRoomService;
    @Autowired
    private UserDeviceToUserDeviceDataConverter userDeviceToUserDeviceDataConverter;

    public UserDeviceData find(String login, String name) {
        UserDevice userDevice = defaultUserDeviceService.find(login, name);
        return isNull(userDevice) ? null : userDeviceToUserDeviceDataConverter.convert(userDevice);
    }

    public UserDevice findModel(String login, String name) {
        return defaultUserDeviceService.find(login, name);
    }

    public List<UserDeviceData> findAllByLogin(String login) {
        List<UserDevice> userDevices = defaultUserDeviceService.find(login);
        return userDevices.stream().map(ud -> userDeviceToUserDeviceDataConverter.convert(ud)).collect(toList());
    }

    public List<UserDeviceData> findAllByLoginAndRoomName(String login, String roomName) {
        List<UserDevice> userDevices = defaultUserDeviceService.findAll(login, roomName);
        return userDevices.stream().map(ud -> userDeviceToUserDeviceDataConverter.convert(ud)).collect(toList());
    }

    public void add(UserDeviceData userDeviceData) {
        defaultUserDeviceService.save(userDeviceData);
    }

    public void remove(String login, String name) {
        defaultUserDeviceService.remove(login, name);
    }

    public List<String> findDeviceTypes(String login) {
        List<Long> roomIds = defaultRoomService.findByLogin(login).stream().map(Room::getRoomId).collect(toList());
        return roomIds.isEmpty() ? emptyList() : defaultUserDeviceService.findDeviceTypes(roomIds);
    }

    public List<Long> findIdsByDeviceName(String login, List<String> devices) {
        return defaultUserDeviceService.findIdsByDeviceNames(login, devices);
    }

    public List<Long> findIdsByDeviceType(String login, String device) {
        return defaultUserDeviceService.findIdsByType(login, device);
    }
}
