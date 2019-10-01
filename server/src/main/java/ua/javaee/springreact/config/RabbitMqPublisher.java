package ua.javaee.springreact.config;

import org.eclipse.paho.client.mqttv3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.stereotype.Component;

@Component
public class RabbitMqPublisher {

    @Value("${spring.rabbitmq.host}")
    private String url;
    @Value("${spring.rabbitmq.username}")
    private String login;

    @Autowired
    private MqttPahoClientFactory mqttClientFactory;

    public void send(String topic, String command, int pin) {
        try {
            String commandWithPin = String.format("%s:%d",command,pin);
            MqttConnectOptions options = mqttClientFactory.getConnectionOptions();
            MqttClient sampleClient = new MqttClient(url, login);
            options.setCleanSession(true);
            sampleClient.connect(options);
            MqttTopic mqtttopic = sampleClient.getTopic(topic);
            MqttMessage message = new MqttMessage(commandWithPin.getBytes());
            message.setQos(1);
            mqtttopic.publish(message);
            sampleClient.disconnect();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
