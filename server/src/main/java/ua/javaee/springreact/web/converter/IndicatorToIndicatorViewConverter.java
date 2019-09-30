package ua.javaee.springreact.web.converter;

import org.springframework.stereotype.Component;
import ua.javaee.springreact.web.entity.Indicator;
import ua.javaee.springreact.web.view.IndicatorView;

@Component
public class IndicatorToIndicatorViewConverter implements AbstractConverter<IndicatorView, Indicator> {
    @Override
    public IndicatorView convert(Indicator source) {
        IndicatorView target = new IndicatorView();
        target.setDate(source.getDate());
        target.setUserDeviceId(source.getUserDeviceId());
        target.setValue(source.getValue());
        return target;
    }
}
