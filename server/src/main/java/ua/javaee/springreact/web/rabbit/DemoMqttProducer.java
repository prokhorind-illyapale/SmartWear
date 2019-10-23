package ua.javaee.springreact.web.rabbit;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;
import ua.javaee.springreact.web.dto.IndicatorDTO;
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

    private Logger logger = LoggerFactory.getLogger(DemoMqttProducer.class);


    public void saveIndicatorValue(Message<?> m) {
        try {
            Indicator i = getIndicator((String) m.getPayload());
            save(i);
        } catch (IOException e) {
            logger.error("Can't save indicator");
            logger.error(e.getMessage());
        }
    }

    private void save(Indicator indicator) {
        indicatorRepository.save(indicator);
    }

    private Indicator getIndicator(String inMsg) throws IOException {
        IndicatorDTO dto = parse(inMsg);
        Indicator indicator = convertIndicator(dto);
        return indicator;
    }

    private Indicator convertIndicator(IndicatorDTO dto) {
        Indicator indicator = new Indicator();
        indicator.setDate(new Date());
        indicator.setId(sequenceGeneratorService.generateSequence(Indicator.SEQUENCE_NAME));
        indicator.setUserDeviceId(dto.getUserDeviceId());
        indicator.setValue(dto.getValue());
        return indicator;
    }

    public IndicatorDTO parse(String inMsg) throws IOException {
        IndicatorDTO indicator = objectMapper.readValue(inMsg, IndicatorDTO.class);
        return indicator;
    }

}
