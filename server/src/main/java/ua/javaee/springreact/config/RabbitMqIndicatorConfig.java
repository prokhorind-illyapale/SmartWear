package ua.javaee.springreact.config;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import ua.javaee.springreact.web.rabbit.DemoMqttProducer;

@Configuration
public class RabbitMqIndicatorConfig {
    @Value("${spring.rabbitmq.host}")
    private String url;
    @Value("${spring.rabbitmq.username}")
    private String login;
    @Value("${spring.rabbitmq.password}")
    private String pass;
    @Value("${spring.rabbitmq.indicator.topic}")
    private String topic;

    @Autowired
    private DemoMqttProducer demoMqttProducer;


    @Bean
    public MqttPahoClientFactory mqttClientFactory() {
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        MqttConnectOptions options = new MqttConnectOptions();
        options.setServerURIs(new String[]{url});
        options.setUserName(login);
        options.setPassword(pass.toCharArray());
        factory.setConnectionOptions(options);
        return factory;
    }

    @Bean
    public IntegrationFlow mqttInbound() {
        return IntegrationFlows.from(
                new MqttPahoMessageDrivenChannelAdapter("4",
                        mqttClientFactory(), topic, "topic2"))
                .handle(m -> demoMqttProducer.save((String) m.getPayload()))
                .get();
    }
}

