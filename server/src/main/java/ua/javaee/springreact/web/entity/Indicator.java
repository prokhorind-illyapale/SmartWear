package ua.javaee.springreact.web.entity;


import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.Date;

@Document
public class Indicator {
    @Transient
    public static final String SEQUENCE_NAME = "indicator_sequence";

    @Id
    private long id;
    private String value;
    private Date date;
    private long userDeviceId;

    public Indicator() {
    }

    public Indicator(String value, Date date, long userDeviceId) {
        this.value = value;
        this.date = date;
        this.userDeviceId = userDeviceId;
    }

    public static String getSequenceName() {
        return SEQUENCE_NAME;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getUserDeviceId() {
        return userDeviceId;
    }

    public void setUserDeviceId(long userDeviceId) {
        this.userDeviceId = userDeviceId;
    }
}
