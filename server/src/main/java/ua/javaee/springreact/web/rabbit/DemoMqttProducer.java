package ua.javaee.springreact.web.rabbit;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;
import ua.javaee.springreact.web.dto.IndicatorDTO;
import ua.javaee.springreact.web.entity.Indicator;
import ua.javaee.springreact.web.entity.UserDevice;
import ua.javaee.springreact.web.repository.IndicatorRepository;
import ua.javaee.springreact.web.service.impl.DefaultMailService;
import ua.javaee.springreact.web.service.impl.DefaultUserDeviceService;
import ua.javaee.springreact.web.service.impl.SequenceGeneratorService;

import java.io.IOException;
import java.util.Date;
import java.util.Objects;

@Service
public class DemoMqttProducer {
    @Autowired
    private DefaultUserDeviceService userDeviceService;
    @Autowired
    private DefaultMailService defaultMailService;
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
            processIndicatorValues(i);
            save(i);
        } catch (IOException e) {
            logger.error("Can't save indicator");
            logger.error(e.getMessage());
        }
    }

    private void save(Indicator indicator) {
        indicatorRepository.save(indicator);
    }

    private void processIndicatorValues(Indicator indicator) {
        UserDevice userDevice = userDeviceService.find(indicator.getUserDeviceId());
        if (Objects.nonNull(userDevice)) {
            Long userDeviceId = userDevice.getUserDeviceId();
            String valueType = userDevice.getValueType();
            if ("GAS".equalsIgnoreCase(valueType)) {
                if (Integer.valueOf(indicator.getValue()) >= 60) {

                    if (Objects.isNull(AlertUtils.get(userDeviceId))) {
                        AlertUtils.add(userDeviceId);
                        defaultMailService.sendAlertMessage(userDevice, indicator.getValue());
                    }

                } else if (Objects.nonNull(AlertUtils.get(userDeviceId))) {
                    AlertUtils.remove(userDeviceId);
                    defaultMailService.sendFixMessage(userDevice, indicator.getValue());
                }
            }
        }
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
        String str = inMsg.substring(0, inMsg.length() - 3);
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append("\"");
        sb.append("}");
        IndicatorDTO indicator = objectMapper.readValue(sb.toString(), IndicatorDTO.class);
        return indicator;
    }

}
