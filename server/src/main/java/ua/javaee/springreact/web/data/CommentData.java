package ua.javaee.springreact.web.data;

import java.util.Date;

/**
 * Created by kleba on 24.02.2019.
 */
public class CommentData {

    private long commentId;
    private String login;
    private String message;
    private LookData lookData;
    private Date lastUpdated;

    public CommentData() {
    }

    public CommentData(long commentId, String login, String message, LookData lookData, Date lastUpdated) {
        this.commentId = commentId;
        this.login = login;
        this.message = message;
        this.lookData = lookData;
        this.lastUpdated = lastUpdated;
    }

    public long getCommentId() {
        return commentId;
    }

    public void setCommentId(long commentId) {
        this.commentId = commentId;
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

    public LookData getLookData() {
        return lookData;
    }

    public void setLookData(LookData lookData) {
        this.lookData = lookData;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
