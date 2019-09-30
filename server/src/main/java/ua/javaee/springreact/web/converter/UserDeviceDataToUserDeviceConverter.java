package ua.javaee.springreact.web.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.javaee.springreact.web.data.UserDeviceData;
import ua.javaee.springreact.web.entity.UserDevice;
import ua.javaee.springreact.web.service.UserService;
import ua.javaee.springreact.web.service.impl.DefaultDeviceService;
import ua.javaee.springreact.web.service.impl.DefaultRoomService;

import static java.util.Objects.nonNull;

@Component
public class UserDeviceDataToUserDeviceConverter implements AbstractConverter<UserDevice, UserDeviceData> {
    @Autowired
    private UserService userService;
    @Autowired
    private DefaultDeviceService defaultDeviceService;
    @Autowired
    private DefaultRoomService defaultRoomService;

    @Override
    public UserDevice convert(UserDeviceData source) {
        UserDevice target = new UserDevice();
        target.setDevice(defaultDeviceService.findByName(source.getDevice().getName()));
        target.setName(source.getName());
        target.setPin(source.getPin());
        target.setValueType(source.getValueType());
        if (nonNull(source.getRoom())) {
            target.setRoom(defaultRoomService.find(source.getRoom().getRoomName(), source.getUser()));
        }
        target.setUser(userService.getUserByLogin(source.getUser()));
        return target;
    }
}
