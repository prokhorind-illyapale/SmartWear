package ua.javaee.springreact.web.data.weatherapi;

/**
 * Created by kleba on 04.03.2018.
 */

public class Main {
    private String humidity;

    private String pressure;

    private String temp_max;

    private String temp_min;

    private String temp;

    public Main() {
    }

    ;

    public Main(String humidity, String pressure, String temp_max, String temp_min, String temp) {
        this.humidity = humidity;
        this.pressure = pressure;
        this.temp_max = temp_max;
        this.temp_min = temp_min;
        this.temp = temp;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getTemp_max() {
        return temp_max;
    }

    public void setTemp_max(String temp_max) {
        this.temp_max = temp_max;
    }

    public String getTemp_min() {
        return temp_min;
    }

    public void setTemp_min(String temp_min) {
        this.temp_min = temp_min;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

}
