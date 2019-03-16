package ua.javaee.springreact.web.data.weatherapi;

/**
 * Created by kleba on 04.03.2018.
 */

public class Wind {

    private String speed;
    private String deg;
    private String gust;

    public Wind() {
    }

    ;

    public Wind(String speed, String deg) {
        this.speed = speed;
        this.deg = deg;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getDeg() {
        return deg;
    }

    public void setDeg(String deg) {
        this.deg = deg;
    }

    public void setGust(String gust) {
        this.gust = gust;
    }

    public String getGust() {
        return gust;
    }
}
