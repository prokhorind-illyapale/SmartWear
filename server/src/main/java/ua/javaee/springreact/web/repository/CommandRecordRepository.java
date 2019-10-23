package ua.javaee.springreact.web.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ua.javaee.springreact.web.entity.CommandRecord;

@Repository
public interface CommandRecordRepository extends MongoRepository<CommandRecord, String> {
}
