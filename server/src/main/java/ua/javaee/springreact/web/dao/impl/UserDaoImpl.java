package ua.javaee.springreact.web.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ua.javaee.springreact.web.dao.UserDao;
import ua.javaee.springreact.web.entity.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by kleba on 09.02.2019.
 */
@Repository
public class UserDaoImpl implements UserDao {

    private static final String IS_USER_EXISTS_QUERY = "SELECT count(u) from User u WHERE u.login=:login";
    private static final long SQL_TRUE = 1l;
    private static final String USER_WITH_NAME_WAS_NOT_FOUND = "User with name {} was not found";
    private static final String USER_WITH_NAME_ALREADY_EXISTS = "User with name {} was found";
    private static final String GET_USER_BY_ID_QUERY = "SELECT u from User u WHERE u.login=:login";
    private static final String GET_ALL_USERS = "SELECT u From User u";
    private static final String DELETE_USER_BY_LOGIN = "DELETE FROM User u WHERE u.login=:login";
    private Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void userReg(User userModel) {
        entityManager.persist(userModel);
    }

    @Override
    public boolean isUserExists(String login) {
        Long exists = (Long) entityManager.createQuery(IS_USER_EXISTS_QUERY)
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

    @Override
    public User getUserByLogin(String login) {
        User user = (User) entityManager.createQuery(GET_USER_BY_ID_QUERY)
                .setParameter("login", login)
                .getSingleResult();
        return user;
    }

    @Override
    public void deleteUserByLogin(String login) {
        entityManager.createQuery(DELETE_USER_BY_LOGIN)
                .setParameter("login", login)
                .executeUpdate();
    }

    @Override
    public List<User> getAllUsers() {
        return entityManager.createQuery(GET_ALL_USERS).getResultList();
    }

    @Override
    public void updateUser(User user) {
        entityManager.persist(user);
        logger.info("User {} was updated", user.getLogin());
    }
}
