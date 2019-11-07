package ua.javaee.springreact.web.facade.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.javaee.springreact.web.converter.IndicatorToIndicatorGraphConverter;
import ua.javaee.springreact.web.converter.IndicatorToIndicatorViewConverter;
import ua.javaee.springreact.web.data.Indicator4GraphData;
import ua.javaee.springreact.web.entity.Indicator;
import ua.javaee.springreact.web.entity.UserDevice;
import ua.javaee.springreact.web.service.impl.DefaultIndicatorService;
import ua.javaee.springreact.web.service.impl.DefaultUserDeviceService;
import ua.javaee.springreact.web.view.IndicatorView;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;

@Component
public class DefaultIndicatorFacade {
    @Autowired
    private DefaultIndicatorService defaultIndicatorService;
    @Autowired
    private DefaultUserDeviceService defaultUserDeviceService;
    @Autowired
    private IndicatorToIndicatorViewConverter indicatorToIndicatorViewConverter;
    @Autowired
    private IndicatorToIndicatorGraphConverter indicatorToIndicatorGraphConverter;

    public List<IndicatorView> getLastValuesInRoom(String login, String roomName) {
        List<Indicator> indicators = defaultIndicatorService.findIndicatorInARoom(login, roomName);
        return indicators.stream().map(indicator -> indicatorToIndicatorViewConverter.convert(indicator)).collect(toList());
    }

    public IndicatorView getLastValue(String login, String name) {
        UserDevice userDevice = defaultUserDeviceService.find(login, name);
        if (Objects.isNull(userDevice)) {
            return null;
        }
        Optional<Indicator> indicator = defaultIndicatorService.getLastValues(singletonList(userDevice.getUserDeviceId())).stream().findFirst();
        return indicator.isPresent() ? indicatorToIndicatorViewConverter.convert(indicator.get()) : null;
    }

    public List<Indicator4GraphData> findBetweenDates(List<Long> userDeviceIds, Date from, Date to) {
        if (userDeviceIds == null || userDeviceIds.isEmpty()) {
            return emptyList();
        }
        List<Indicator> indicators = defaultIndicatorService.findBetweenDates(userDeviceIds, from, to);
        return indicators.stream().map(indicatorToIndicatorGraphConverter::convert).collect(toList());
    }
}
