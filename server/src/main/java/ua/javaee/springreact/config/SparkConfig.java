package ua.javaee.springreact.config;

import org.apache.spark.SparkConf;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SparkConfig {

    @Value("${spring.data.mongodb.uri}")
    private String mongoDbConnectionUri;

    @Bean
    public SparkConf processSparkConfig() {
        SparkConf sparkConf = new SparkConf()
                .setMaster("local")
                .set("spark.mongodb.input.uri", mongoDbConnectionUri + ".indicator")
                .set("spark.driver.allowMultipleContexts", "true")
                .setAppName("SparkMongoConnectorPOC");
        return sparkConf;
    }
}
