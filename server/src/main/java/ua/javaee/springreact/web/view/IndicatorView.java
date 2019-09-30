package ua.javaee.springreact.web.view;

import java.util.Date;

public class IndicatorView {
    private String value;
    private long userDeviceId;
    private Date date;

    public IndicatorView() {
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public long getUserDeviceId() {
        return userDeviceId;
    }

    public void setUserDeviceId(long userDeviceId) {
        this.userDeviceId = userDeviceId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
