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
    private String city;
    private String sex;

    public RegistryUserForm() {
    }

    public RegistryUserForm(@Email String email, @NotNull String login, @NotNull String password, String city, String sex) {
        this.email = email;
        this.login = login;
        this.password = password;
        this.city = city;
        this.sex = sex;
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

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSex() {
        return sex;
    }
}
