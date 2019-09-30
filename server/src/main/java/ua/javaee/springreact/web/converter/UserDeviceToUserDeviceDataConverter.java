package ua.javaee.springreact.web.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.javaee.springreact.web.data.UserData;
import ua.javaee.springreact.web.data.UserDeviceData;
import ua.javaee.springreact.web.entity.UserDevice;
import ua.javaee.springreact.web.populator.UserToUserDataPopulator;
import ua.javaee.springreact.web.service.UserService;

import java.util.List;

import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toList;

@Component
public class UserDeviceToUserDeviceDataConverter implements AbstractConverter<UserDeviceData, UserDevice> {
    @Autowired
    private UserService userService;
    @Autowired
    private UserToUserDataPopulator userToUserDataPopulator;
    @Autowired
    private DeviceToDeviceDataConverter deviceToDeviceDataConverter;
    @Autowired
    private RoomToRoomDataConverter roomToRoomDataConverter;

    @Override
    public UserDeviceData convert(UserDevice source) {
        UserDeviceData target = new UserDeviceData();
        target.setName(source.getName());
        target.setPin(source.getPin());
        target.setValueType(source.getValueType());
        target.setDevice(deviceToDeviceDataConverter.convert(source.getDevice()));
        if (nonNull(source.getRoom())) {
            target.setRoom(roomToRoomDataConverter.convert(source.getRoom()));
        }
        populateUser(source, target);
        return target;
    }

    public List<UserDeviceData> convertAll(List<UserDevice> source) {
        return source.stream().map(this::convert).collect(toList());
    }

    private void populateUser(UserDevice source, UserDeviceData target) {
        UserData userData = new UserData();
        userToUserDataPopulator.populate(source.getUser(), userData);
        target.setUser(userData.getLogin());
    }
}
