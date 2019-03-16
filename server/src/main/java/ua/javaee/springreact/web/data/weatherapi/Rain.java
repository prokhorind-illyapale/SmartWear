package ua.javaee.springreact.web.data.weatherapi;


import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by kleba on 16.03.2019.
 */
public class Rain {

    @JsonProperty("3h")
    private String mm;

    public Rain() {
    }

    ;

    public void setMm(String mm) {
        this.mm = mm;
    }

    public String getMm() {
        return mm;
    }
}
