package ua.javaee.springreact.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;
import ua.javaee.springreact.web.entity.Indicator;
import ua.javaee.springreact.web.repository.IndicatorRepository;

import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.sort;


@Service
public class DefaultIndicatorService {

    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private IndicatorRepository indicatorRepository;
    @Autowired
    private DefaultUserDeviceService defaultUserDeviceService;


    public List<Indicator> findIndicatorInARoom(String login, String roomName) {
        List<Long> ids = defaultUserDeviceService.findUserDeviceIdsInRoom(login, roomName);
        return getLastValues(ids);
    }

    public List<Indicator> getLastValues(List<Long> deviceIds) {
        GroupOperation groupOperation = Aggregation.group("userDeviceId")
                .addToSet("userDeviceId").as("userDeviceId")
                .last("date").as("date")
                .last("id").as("id")
                .last("value").as("value");
        MatchOperation matchOperation = match(Criteria.where("userDeviceId").in(deviceIds));
        SortOperation sortOperation = sort(Sort.Direction.DESC, "userDeviceId");

        TypedAggregation<Indicator> studentAggregation = Aggregation.newAggregation(Indicator.class, matchOperation, groupOperation, sortOperation);

        AggregationResults<Indicator> results = mongoTemplate.
                aggregate(studentAggregation, "indicator", Indicator.class);

        return results.getMappedResults();
    }
}
