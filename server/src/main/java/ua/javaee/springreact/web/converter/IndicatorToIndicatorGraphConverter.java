package ua.javaee.springreact.web.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.javaee.springreact.web.data.Indicator4GraphData;
import ua.javaee.springreact.web.entity.Indicator;
import ua.javaee.springreact.web.entity.UserDevice;
import ua.javaee.springreact.web.service.impl.DefaultUserDeviceService;

@Component
public class IndicatorToIndicatorGraphConverter implements AbstractConverter<Indicator4GraphData, Indicator> {

    @Autowired
    private DefaultUserDeviceService defaultUserDeviceService;

    @Override
    public Indicator4GraphData convert(Indicator source) {
        UserDevice userDevice = defaultUserDeviceService.find(source.getUserDeviceId());
        Indicator4GraphData target = new Indicator4GraphData();
        target.setDate(source.getDate());
        target.setDeviceName(userDevice.getName());
        target.setValue(source.getValue());
        target.setType(userDevice.getValueType());
        return target;
    }
}
