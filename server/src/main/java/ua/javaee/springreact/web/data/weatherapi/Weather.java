package ua.javaee.springreact.web.data.weatherapi;

/**
 * Created by kleba on 04.03.2018.
 */

public class Weather {

    private String id;

    private String icon;

    private String description;

    private String main;

    public String getId() {
        return id;
    }

    public Weather() {
    }

    ;

    public Weather(String id, String icon, String description, String main) {
        this.id = id;
        this.icon = icon;
        this.description = description;
        this.main = main;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }
}
