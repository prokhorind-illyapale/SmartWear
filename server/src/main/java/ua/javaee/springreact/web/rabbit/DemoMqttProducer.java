package ua.javaee.springreact.web.rabbit;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.javaee.springreact.web.entity.Indicator;
import ua.javaee.springreact.web.repository.IndicatorRepository;
import ua.javaee.springreact.web.service.impl.SequenceGeneratorService;

import java.io.IOException;
import java.util.Date;

@Service
public class DemoMqttProducer {
    @Autowired
    private IndicatorRepository indicatorRepository;
    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;
    @Autowired
    private ObjectMapper objectMapper;

    public void save(String inMsg) {
        try {
            ua.javaee.springreact.web.dto.Indicator dto = parse(inMsg);
            Indicator indicator = convertIndicator(dto);
            indicatorRepository.save(indicator);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Indicator convertIndicator(ua.javaee.springreact.web.dto.Indicator dto) {
        Indicator indicator = new Indicator();
        indicator.setDate(new Date());
        indicator.setId(sequenceGeneratorService.generateSequence(Indicator.SEQUENCE_NAME));
        indicator.setUserDeviceId(dto.getUserDeviceId());
        indicator.setValue(dto.getValue());
        return indicator;
    }

    public ua.javaee.springreact.web.dto.Indicator parse(String inMsg) throws IOException {
        ua.javaee.springreact.web.dto.Indicator indicator = objectMapper.readValue(inMsg, ua.javaee.springreact.web.dto.Indicator.class);
        return indicator;
    }

}
