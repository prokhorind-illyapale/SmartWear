package ua.javaee.springreact.web.form.lookforms;

/**
 * Created by kleba on 24.02.2019.
 */
public class SexDataForm {

    private String name;

    public SexDataForm() {
    }

    public SexDataForm(String name) {

        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
