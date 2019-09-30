package ua.javaee.springreact.web.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ua.javaee.springreact.web.entity.Indicator;

@Repository
public interface IndicatorRepository extends MongoRepository<Indicator, String> {

    //List<Indicator> findTopByOrderByUserDeviceIdDescAndUserDeviceIdIn(List<Long> userDeviceIdList);

    //List<Indicator>  findByUserDeviceIdIn(List<Long> userDeviceIdList);
}
