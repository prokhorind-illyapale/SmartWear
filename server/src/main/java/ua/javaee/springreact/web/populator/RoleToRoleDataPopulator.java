package ua.javaee.springreact.web.populator;

import org.springframework.stereotype.Component;
import ua.javaee.springreact.web.data.RoleData;
import ua.javaee.springreact.web.entity.Role;

/**
 * Created by kleba on 11.02.2019.
 */
@Component
public class RoleToRoleDataPopulator implements AbstractPopulator<Role, RoleData> {
    @Override
    public void populate(Role source, RoleData target) {
        target.setRoleName(source.getName());
    }
}
