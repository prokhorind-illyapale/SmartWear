package ua.javaee.springreact.web.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.javaee.springreact.web.data.RoomData;
import ua.javaee.springreact.web.data.UserData;
import ua.javaee.springreact.web.entity.Room;
import ua.javaee.springreact.web.populator.UserToUserDataPopulator;

@Component
public class RoomToRoomDataConverter implements AbstractConverter<RoomData, Room> {

    @Autowired
    private UserToUserDataPopulator userToUserDataPopulator;
    @Autowired
    private UserDeviceToUserDeviceDataConverter userDeviceToUserDeviceDataConverter;

    @Override
    public RoomData convert(Room source) {
        RoomData target = new RoomData();
        populateUser(source, target);
        target.setRoomName(source.getRoomName());
        return target;
    }

    private void populateUser(Room source, RoomData target) {
        UserData userData = new UserData();
        userToUserDataPopulator.populate(source.getUser(), userData);
    }
}
