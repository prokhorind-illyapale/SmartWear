package ua.javaee.springreact.web.facade.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.javaee.springreact.web.converter.IndicatorToIndicatorViewConverter;
import ua.javaee.springreact.web.entity.Indicator;
import ua.javaee.springreact.web.service.impl.DefaultIndicatorService;
import ua.javaee.springreact.web.view.IndicatorView;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class DefaultIndicatorFacade {
    @Autowired
    private DefaultIndicatorService defaultIndicatorService;
    @Autowired
    private IndicatorToIndicatorViewConverter indicatorToIndicatorViewConverter;

    public List<IndicatorView> getLastValuesInRoom(String login, String roomName) {
        List<Indicator> indicators = defaultIndicatorService.findIndicatorInARoom(login, roomName);
        return indicators.stream().map(indicator -> indicatorToIndicatorViewConverter.convert(indicator)).collect(toList());
    }
}
