package ua.javaee.springreact.web.form;

import com.fasterxml.jackson.annotation.JsonProperty;
import ua.javaee.springreact.web.data.RoleData;

/**
 * Created by kleba on 11.02.2019.
 */
public class UserForm {

    @JsonProperty
    private String email;
    @JsonProperty
    private String login;
    @JsonProperty
    private String city;
    @JsonProperty
    private String sex;
    private RoleData userRole;

    public UserForm() {
    }

    ;

    public UserForm(String email, String login, String city, String sex, RoleData userRole) {
        this.email = email;
        this.login = login;
        this.city = city;
        this.sex = sex;
        this.userRole = userRole;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public RoleData getUserRole() {
        return userRole;
    }

    public void setUserRole(RoleData userRole) {
        this.userRole = userRole;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSex() {
        return sex;
    }
}
