package ua.javaee.springreact.web.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.javaee.springreact.web.data.UserDeviceData;
import ua.javaee.springreact.web.facade.impl.DefaultDeviceFacade;
import ua.javaee.springreact.web.facade.impl.DefaultRoomFacade;
import ua.javaee.springreact.web.form.UserDeviceDataForm;

@Component
public class UserDeviceDataFormToUserDeviceDataConverter implements AbstractConverter<UserDeviceData, UserDeviceDataForm> {
    @Autowired
    private DefaultRoomFacade defaultRoomFacade;
    @Autowired
    private DefaultDeviceFacade defaultDeviceFacade;

    @Override
    public UserDeviceData convert(UserDeviceDataForm source) {
        UserDeviceData target = new UserDeviceData();
        target.setRoom(defaultRoomFacade.find(source.getRoom(), source.getUser()));
        target.setDevice(defaultDeviceFacade.findByName(source.getDevice()));
        target.setName(source.getName());
        target.setUser(source.getUser());
        return target;
    }
}
