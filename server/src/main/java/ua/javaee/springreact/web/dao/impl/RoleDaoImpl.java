package ua.javaee.springreact.web.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ua.javaee.springreact.web.dao.RoleDao;
import ua.javaee.springreact.web.entity.Role;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 * Created by kleba on 09.02.2019.
 */
@Repository
public class RoleDaoImpl implements RoleDao {

    private static final String GET_ROLE_BY_NAME = "Select r From Role r  where r.name=:name";
    private static final String GET_ROLE_BY_USER_LOGIN = "SELECT r From Role r , User u where u.login=:login and u.role.roleId = r.roleId";
    @PersistenceContext
    private EntityManager entityManager;

    private Logger logger = LoggerFactory.getLogger(RoleDaoImpl.class);

    public Role getRoleByName(String name) {
        return (Role) entityManager.createQuery(GET_ROLE_BY_NAME).setParameter("name",name ).getSingleResult();
    }

    @Override
    public Role getRoleByLogin(String login) {
        Role role = null;
        try {
            role = (Role) entityManager.createQuery(GET_ROLE_BY_USER_LOGIN).setParameter("login", login).getSingleResult();
            return role;
        } catch (NoResultException e) {
            logger.info("Can't find role for user: {}", login);
        }
        return role;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }
}
