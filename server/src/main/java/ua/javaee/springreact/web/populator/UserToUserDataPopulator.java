package ua.javaee.springreact.web.populator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.javaee.springreact.web.data.RoleData;
import ua.javaee.springreact.web.data.UserData;
import ua.javaee.springreact.web.entity.User;

/**
 * Created by kleba on 11.02.2019.
 */
@Component
public class UserToUserDataPopulator implements AbstractPopulator<User, UserData> {
    @Autowired
    private RoleToRoleDataPopulator populator;
    @Override
    public void populate(User source, UserData target) {
        RoleData roleData = new RoleData();
        populator.populate(source.getRole(), roleData);
        target.setCity(source.getCity());
        target.setUserRole(roleData);
        target.setLogin(source.getLogin());
        target.setEmail(source.getEmail());
    }
}
