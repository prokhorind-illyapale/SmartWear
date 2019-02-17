package ua.javaee.springreact.web.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ua.javaee.springreact.web.entity.Role;

/**
 * Created by kleba on 09.02.2019.
 */
public interface RoleDao extends JpaRepository<Role, Long> {

    Role findByName(String name);

    @Query("SELECT r From Role r , User u where u.login=:login and u.role.roleId = r.roleId")
    Role getRoleByLogin(@Param("login") String login);
}
