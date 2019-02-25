package ua.javaee.springreact.web.populator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.javaee.springreact.web.data.UserData;
import ua.javaee.springreact.web.form.UserForm;

/**
 * Created by kleba on 11.02.2019.
 */
@Component
public class UserDataToUserFormPopulator implements AbstractPopulator<UserData, UserForm> {

    @Autowired(required = true)
    RoleToRoleDataPopulator roleToRoleDataPopulator;

    @Override
    public void populate(UserData source, UserForm target) {
        target.setEmail(source.getEmail());
        target.setLogin(source.getLogin());
        target.setUserRole(source.getUserRole());
        target.setCity(source.getCity());
        target.setSex(source.getSex());
    }
}
