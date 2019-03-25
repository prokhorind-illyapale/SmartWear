package ua.javaee.springreact.web.form;

/**
 * Created by kleba on 25.03.2019.
 */
public class ChangePasswordForm {
    private String login;
    private String oldPassword;
    private String newPassword;

    public ChangePasswordForm() {
    }

    ;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
