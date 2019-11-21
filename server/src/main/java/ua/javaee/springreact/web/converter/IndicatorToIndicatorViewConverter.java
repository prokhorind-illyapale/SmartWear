package ua.javaee.springreact.web.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.javaee.springreact.web.entity.Indicator;
import ua.javaee.springreact.web.entity.UserDevice;
import ua.javaee.springreact.web.service.impl.DefaultUserDeviceService;
import ua.javaee.springreact.web.view.IndicatorView;

import static java.util.Objects.nonNull;

@Component
public class IndicatorToIndicatorViewConverter implements AbstractConverter<IndicatorView, Indicator> {

    @Autowired
    private DefaultUserDeviceService defaultUserDeviceService;

    @Override
    public IndicatorView convert(Indicator source) {
        UserDevice userDevice = defaultUserDeviceService.find(source.getUserDeviceId());
        IndicatorView target = new IndicatorView();
        target.setDate(source.getDate());
        if (nonNull(userDevice)) {
            target.setDeviceName(userDevice.getName());
        }
        target.setValue(source.getValue());
        return target;
    }
}
