package ua.javaee.springreact.web.data.weatherapi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by kleba on 04.03.2018.
 */

public class Sys {
    private String message;

    private String id;

    private Date sunset;

    private Date sunrise;

    private String type;

    private String country;

    public Sys() {
    }

    ;

    public Sys(String message, String id, Date sunset, Date sunrise, String type, String country) {
        this.message = message;
        this.id = id;
        this.sunset = sunset;
        this.sunrise = sunrise;
        this.type = type;
        this.country = country;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getSunset() {
        return sunset;
    }

    public void setSunset(String sunset) throws ParseException {
        SimpleDateFormat dateFormatGmt = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");
        dateFormatGmt.setTimeZone(TimeZone.getTimeZone("GMT+4"));
        SimpleDateFormat dateFormatLocal = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");
        Date date = Date.from(Instant.ofEpochSecond(Long.valueOf(sunset)));
        this.sunset = dateFormatLocal.parse(dateFormatGmt.format(date));

    }

    public Date getSunrise() {
        return sunrise;
    }

    public void setSunrise(String sunrise) throws ParseException {

        SimpleDateFormat dateFormatGmt = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");
        dateFormatGmt.setTimeZone(TimeZone.getTimeZone("GMT+4"));
        SimpleDateFormat dateFormatLocal = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");
        Date date = Date.from(Instant.ofEpochSecond(Long.valueOf(sunrise)));
        this.sunrise = dateFormatLocal.parse(dateFormatGmt.format(date));
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
