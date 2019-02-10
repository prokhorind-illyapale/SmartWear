package ua.javaee.springreact.web.populator;

import org.springframework.stereotype.Component;
import ua.javaee.springreact.web.data.UserData;
import ua.javaee.springreact.web.entity.User;

/**
 * Created by kleba on 09.02.2019.
 */
@Component
public class UserDataToUserModelPopulator implements  AbstractPopulator<UserData,User> {
    @Override
    public void populate(UserData source, User target) {
        target.setEmail(source.getEmail());
        target.setLogin(source.getLogin());
        target.setPassword(source.getPassword());
        target.setRole(source.getUserRole());
    }
}
