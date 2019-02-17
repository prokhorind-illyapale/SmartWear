package ua.javaee.springreact.web.populator;

import org.springframework.stereotype.Component;
import ua.javaee.springreact.web.data.UserData;
import ua.javaee.springreact.web.form.UserForm;

/**
 * Created by kleba on 17.02.2019.
 */
@Component
public class UserFormToUserDataPopulator implements AbstractPopulator<UserForm, UserData> {
    @Override
    public void populate(UserForm source, UserData target) {
        target.setEmail(source.getEmail());
        target.setCity(source.getCity());
        target.setUserRole(source.getUserRole());
        target.setLogin(source.getLogin());
    }
}
