package ua.javaee.springreact.web.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.Date;
import java.util.Objects;

@Document
public class CommandRecord {

    @Transient
    public static final String SEQUENCE_NAME = "command_sequence";

    @Id
    private long id;
    private String command;
    private Date date;
    private String userDeviceName;
    private String login;

    public CommandRecord() {
    }

    public CommandRecord(long id, String command, Date date, String userDeviceName, String login) {
        this.id = id;
        this.command = command;
        this.date = date;
        this.userDeviceName = userDeviceName;
        this.login = login;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setUserDeviceName(String userDeviceName) {
        this.userDeviceName = userDeviceName;
    }

    public String getUserDeviceName() {
        return userDeviceName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommandRecord that = (CommandRecord) o;
        return id == that.id &&
                userDeviceName == that.userDeviceName &&
                Objects.equals(command, that.command) &&
                Objects.equals(date, that.date) &&
                Objects.equals(login, that.login);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, command, date, userDeviceName, login);
    }
}
