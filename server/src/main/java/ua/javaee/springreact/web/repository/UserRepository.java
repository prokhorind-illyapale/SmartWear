package ua.javaee.springreact.web.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.javaee.springreact.web.entity.User;

/**
 * Created by kleba on 09.02.2019.
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    Long countByLogin(String login);

    User findByLogin(String login);

    void deleteByLogin(String login);
}
