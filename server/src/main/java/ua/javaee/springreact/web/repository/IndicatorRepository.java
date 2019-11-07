package ua.javaee.springreact.web.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import ua.javaee.springreact.web.entity.Indicator;

import java.util.Date;
import java.util.List;

@Repository
public interface IndicatorRepository extends MongoRepository<Indicator, String> {

    List<Indicator> findByUserDeviceIdIn(List<Long> userDeviceIdList);

    @Query("{userDeviceId: {$in: ?0},date: {$gt: ?1, $lt: ?2}}")
    List<Indicator> findByDateBetweenAndLogin(List<Long> userDeviceIds, Date from, Date to);
}
