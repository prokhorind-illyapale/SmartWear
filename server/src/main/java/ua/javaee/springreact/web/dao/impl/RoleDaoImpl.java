package ua.javaee.springreact.web.dao.impl;

import org.springframework.stereotype.Repository;
import ua.javaee.springreact.web.dao.RoleDao;
import ua.javaee.springreact.web.entity.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by kleba on 09.02.2019.
 */
@Repository
public class RoleDaoImpl implements RoleDao {

    private static final String GET_ROLE_BY_NAME = "Select r From Role r  where r.name=:name";
    @PersistenceContext
    private EntityManager entityManager;

    public Role getRoleByName(String name) {
        return (Role) entityManager.createQuery(GET_ROLE_BY_NAME).setParameter("name",name ).getSingleResult();
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }
}
