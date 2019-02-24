package ua.javaee.springreact.web.form.lookforms;

import java.util.Date;

/**
 * Created by kleba on 25.02.2019.
 */
public class CommentForm {

    private String login;
    private String message;
    private Date lastUpdated;

    public CommentForm() {
    }

    public CommentForm(String login, String message, Date lastUpdated) {
        this.login = login;
        this.message = message;
        this.lastUpdated = lastUpdated;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
