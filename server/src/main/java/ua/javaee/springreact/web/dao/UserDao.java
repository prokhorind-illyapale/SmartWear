package ua.javaee.springreact.web.dao;

import org.springframework.data.repository.CrudRepository;
import ua.javaee.springreact.web.entity.User;

/**
 * Created by kleba on 09.02.2019.
 */
public interface UserDao extends CrudRepository<User, Long> {

    Long countByLogin(String login);

    User findByLogin(String login);

    void deleteByLogin(String login);
}
