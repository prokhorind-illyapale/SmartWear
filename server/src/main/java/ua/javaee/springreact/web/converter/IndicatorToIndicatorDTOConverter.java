package ua.javaee.springreact.web.converter;

import org.springframework.stereotype.Component;
import ua.javaee.springreact.web.dto.IndicatorDTO;
import ua.javaee.springreact.web.entity.Indicator;

@Component
public class IndicatorToIndicatorDTOConverter implements AbstractConverter<IndicatorDTO, Indicator> {
    @Override
    public IndicatorDTO convert(Indicator source) {
        IndicatorDTO target = new IndicatorDTO();
        target.setId(source.getId());
        target.setUserDeviceId(source.getUserDeviceId());
        target.setValue(source.getValue());
        return target;
    }
}
