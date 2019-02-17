package ua.javaee.springreact.web.populator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import ua.javaee.springreact.web.data.UserData;
import ua.javaee.springreact.web.form.RegistryUserForm;

/**
 * Created by kleba on 09.02.2019.
 */
@Component
public class RegUserFormToUserDataPopulator implements AbstractPopulator<RegistryUserForm,UserData>{

    private static final String ROLE_NAME = "USER";
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void populate(RegistryUserForm source, UserData target){
        target.setEmail(source.getEmail());
        target.setLogin(source.getLogin());
        target.setPassword(bCryptPasswordEncoder.encode(source.getPassword()));
        target.setCity(source.getCity());
    }
}
