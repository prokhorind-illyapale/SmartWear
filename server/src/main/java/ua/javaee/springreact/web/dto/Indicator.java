package ua.javaee.springreact.web.dto;

public class Indicator {
    private long id;
    private String value;
    private long userDeviceId;

    public Indicator(long id, String value, long userDeviceId) {
        this.id = id;
        this.value = value;
        this.userDeviceId = userDeviceId;
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


    public long getUserDeviceId() {
        return userDeviceId;
    }

    public void setUserDeviceId(long userDeviceId) {
        this.userDeviceId = userDeviceId;
    }
}
