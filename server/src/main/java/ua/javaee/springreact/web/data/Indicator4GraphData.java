package ua.javaee.springreact.web.data;

import java.util.Date;

public class Indicator4GraphData {
    private String value;
    private String deviceName;
    private String type;
    private Date date;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
