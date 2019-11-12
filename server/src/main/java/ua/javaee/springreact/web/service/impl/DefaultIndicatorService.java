package ua.javaee.springreact.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;
import ua.javaee.springreact.web.entity.Indicator;
import ua.javaee.springreact.web.repository.IndicatorRepository;
import ua.javaee.springreact.web.util.DateUtils;

import java.util.Date;
import java.util.List;

import static java.util.Objects.isNull;
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

    public List<Indicator> getValues(List<Long> deviceIds) {
        return indicatorRepository.findByUserDeviceIdIn(deviceIds);
    }

    public List<Indicator> findBetweenDates(List<Long> userDeviceIds, Date from, Date to) {
        if (isNull(to)) {
            to = DateUtils.parseDate(new Date());
        }
        if (isNull(from)) {
            from = DateUtils.parseDate(new Date(0));
        }
        return indicatorRepository.findByDateBetweenAndUserDeviceIds(userDeviceIds, from, to);
    }
}
