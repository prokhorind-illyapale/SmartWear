package ua.javaee.springreact.web.entity;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by kleba on 24.02.2019.
 */
@Entity
@Table
public class Comment {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "comment_id")
    private long commentId;
    @Column
    private String login;
    @Column
    private String message;
    @Column
    private Date lastUpdated;
    @ManyToOne
    @JoinColumn(name = "look_id")
    private Look look;

    public Comment() {
    }

    public Comment(String login, String message, Date lastUpdated, Look look) {
        this.login = login;
        this.message = message;
        this.lastUpdated = lastUpdated;
        this.look = look;
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

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Look getLook() {
        return look;
    }

    public void setLook(Look look) {
        this.look = look;
    }
}
