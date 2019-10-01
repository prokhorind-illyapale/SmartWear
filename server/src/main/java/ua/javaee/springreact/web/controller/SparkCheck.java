package ua.javaee.springreact.web.controller;

import com.mongodb.spark.MongoSpark;
import com.mongodb.spark.config.ReadConfig;
import com.mongodb.spark.rdd.api.java.JavaMongoRDD;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.javaee.springreact.web.repository.IndicatorRepository;
import ua.javaee.springreact.web.service.impl.DefaultIndicatorService;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/spark")
public class SparkCheck {

    @Autowired
    private SparkConf sparkConf;
    @Autowired
    private DefaultIndicatorService defaultIndicatorService;

    @Autowired
    private IndicatorRepository indicatorRepository;

    @GetMapping
    public ResponseEntity check() {

        ReadConfig tweetsReadConfig = ReadConfig.create(sparkConf);
        JavaSparkContext javaSparkContext = new JavaSparkContext(sparkConf);

        JavaMongoRDD<Document> tweetsRdd = MongoSpark.load(javaSparkContext, tweetsReadConfig);

        String matchQuery = "\"_class\": \"ua.javaee.springreact.web.entity.Indicator\"";

        JavaRDD<Document> tweetsJavaRdd = tweetsRdd.withPipeline(Collections.singletonList(
                Document.parse("{ $match: { " + matchQuery + " } }")
        ));
        JavaRDD<Long> tweetTexts = tweetsJavaRdd.map(singleRdd -> {
            return singleRdd.getLong("_id");
        });

        List<Long> tweets = tweetTexts.collect();
        long tweetsCount = tweetTexts.count();
        return ResponseEntity.ok(tweetsCount);

    }
}
