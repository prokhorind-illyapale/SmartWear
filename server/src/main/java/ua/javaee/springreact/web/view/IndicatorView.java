package ua.javaee.springreact.web.view;

import java.util.Date;

public class IndicatorView {
    private String value;
    private String deviceName;
    private Date date;

    public IndicatorView() {
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
