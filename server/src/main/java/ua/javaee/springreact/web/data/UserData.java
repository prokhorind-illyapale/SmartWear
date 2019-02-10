package ua.javaee.springreact.web.data;

import ua.javaee.springreact.web.entity.Role;

/**
 * Created by kleba on 09.02.2019.
 */
public class UserData {

    private String email;
    private String login;
    private String password;
    private Role userRole;

    public UserData() {
    }

    public UserData(String email, String login, String password, Role userRole) {
        this.email = email;
        this.login = login;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserRole(Role userRole) {
        this.userRole = userRole;
    }

    public Role getUserRole() {
        return userRole;
    }
}
