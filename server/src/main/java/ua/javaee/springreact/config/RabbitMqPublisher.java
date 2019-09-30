package ua.javaee.springreact.config;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
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
    private MqttPahoClientFactory mqttPahoClientFactory;

    public void send(String topic, String command, int pin) throws MqttException {
        MqttConnectOptions options = mqttPahoClientFactory.getConnectionOptions();
        MqttClient sampleClient = new MqttClient(url, login);
        MqttConnectOptions connOpts = new MqttConnectOptions();
        connOpts.setCleanSession(true);
        sampleClient.connect(connOpts);
        MqttMessage message = new MqttMessage(command.getBytes());
        sampleClient.publish(String.format("%s/%d", topic, pin), message);
        sampleClient.disconnect();
    }
}
