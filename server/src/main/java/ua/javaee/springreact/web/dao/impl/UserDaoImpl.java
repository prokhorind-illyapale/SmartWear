package ua.javaee.springreact.web.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ua.javaee.springreact.web.dao.UserDao;
import ua.javaee.springreact.web.entity.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by kleba on 09.02.2019.
 */
@Repository
public class UserDaoImpl implements UserDao {

    private static final String IS_USER_EXISTS = "SELECT count(u) from User u WHERE u.login=:login";
    private static final long SQL_TRUE = 1l;
    private static final String USER_WITH_NAME_WAS_NOT_FOUND = "User with name {} was not found";
    private static final String USER_WITH_NAME_ALREADY_EXISTS = "User with name {} already exists";
    private Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void userReg(User userModel) {
        entityManager.persist(userModel);
    }

    @Override
    public boolean isUserExists(String login) {
        Long exists = (Long) entityManager.createQuery(IS_USER_EXISTS)
                .setParameter("login", login)
                .getSingleResult();
        if (exists == SQL_TRUE) {
            logger.info(USER_WITH_NAME_ALREADY_EXISTS,login);
            return true;
        } else {
            logger.info(USER_WITH_NAME_WAS_NOT_FOUND,login);
            return false;
        }
    }
}
