package ua.javaee.springreact.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.javaee.springreact.web.converter.UserDeviceDataToUserDeviceConverter;
import ua.javaee.springreact.web.data.UserDeviceData;
import ua.javaee.springreact.web.entity.UserDevice;
import ua.javaee.springreact.web.repository.UserDeviceRepository;

import java.util.List;

@Service
public class DefaultUserDeviceService {
    @Autowired
    private UserDeviceRepository userDeviceRepository;
    @Autowired
    private UserDeviceDataToUserDeviceConverter userDeviceDataToUserDeviceConverter;

    public UserDevice find(String login, String name) {
        return userDeviceRepository.findByUserLoginAndName(login, name);
    }

    public List<UserDevice> find(String login) {
        return userDeviceRepository.findAllByByUserLogin(login);
    }

    public List<UserDevice> findAll(String login, String roomName) {
        return userDeviceRepository.findAllByUserLoginAndRoomName(login, roomName);
    }

    public void save(UserDeviceData userDeviceData) {
        UserDevice userDevice = userDeviceDataToUserDeviceConverter.convert(userDeviceData);
        userDeviceRepository.save(userDevice);
    }

    public void remove(String login, String name) {
        UserDevice userDevice = userDeviceRepository.findByUserLoginAndName(login, name);
        userDeviceRepository.delete(userDevice);
    }
}
