package ua.javaee.springreact.web.populator;

import org.springframework.stereotype.Component;
import ua.javaee.springreact.web.data.UserData;
import ua.javaee.springreact.web.entity.User;

/**
 * Created by kleba on 11.02.2019.
 */
@Component
public class UserToUserDataPopulator implements AbstractPopulator<User, UserData> {
    @Override
    public void populate(User source, UserData target) {
        target.setCity(source.getCity());
        target.setUserRole(source.getRole());
        target.setLogin(source.getLogin());
        target.setEmail(source.getEmail());
    }
}
