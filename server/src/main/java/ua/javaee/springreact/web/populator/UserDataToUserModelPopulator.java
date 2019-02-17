package ua.javaee.springreact.web.populator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.javaee.springreact.web.data.UserData;
import ua.javaee.springreact.web.entity.User;
import ua.javaee.springreact.web.service.UserService;

/**
 * Created by kleba on 09.02.2019.
 */
@Component
public class UserDataToUserModelPopulator implements  AbstractPopulator<UserData,User> {
    @Autowired
    UserService userService;
    @Override
    public void populate(UserData source, User target) {
        target.setEmail(source.getEmail());
        target.setLogin(source.getLogin());
        target.setPassword(source.getPassword());
        target.setRole(userService.getRoleByLogin(source.getLogin()));
        target.setCity(source.getCity());
    }
}
