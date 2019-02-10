package ua.javaee.springreact.web.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

/**
 * Created by kleba on 09.02.2019.
 */
public class RegistryUserForm {
    @Email
    private String email;
    @NotNull
    private String login;
    @NotNull
    private String password;

    public RegistryUserForm() {
    }

    public RegistryUserForm(String email, String login, String password) {
        this.email = email;
        this.login = login;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
