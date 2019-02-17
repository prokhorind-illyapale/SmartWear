package ua.javaee.springreact.web.data;

/**
 * Created by kleba on 09.02.2019.
 */
public class UserData {

    private String email;
    private String login;
    private String password;
    private String city;
    private RoleData userRole;

    public UserData() {
    }

    public UserData(String email, String login, String password, String city, RoleData userRole) {
        this.email = email;
        this.login = login;
        this.password = password;
        this.city = city;
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

    public void setUserRole(RoleData userRole) {
        this.userRole = userRole;
    }

    public RoleData getUserRole() {
        return userRole;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }
}
