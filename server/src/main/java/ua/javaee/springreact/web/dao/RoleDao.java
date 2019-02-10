package ua.javaee.springreact.web.dao;

import ua.javaee.springreact.web.entity.Role;

/**
 * Created by kleba on 09.02.2019.
 */
public interface RoleDao {

    Role getRoleByName(String name);
}
