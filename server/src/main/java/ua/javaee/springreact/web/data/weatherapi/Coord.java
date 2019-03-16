package ua.javaee.springreact.web.data.weatherapi;

/**
 * Created by kleba on 04.03.2018.
 */

public class Coord {

    private String lon;

    private String lat;

    public Coord() {
    }

    ;

    public Coord(String lon, String lat) {
        this.lon = lon;
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }
}
