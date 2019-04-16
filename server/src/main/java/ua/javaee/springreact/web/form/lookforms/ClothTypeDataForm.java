package ua.javaee.springreact.web.form.lookforms;

import javax.validation.constraints.NotEmpty;

/**
 * Created by kleba on 24.02.2019.
 */
public class ClothTypeDataForm {

    @NotEmpty(message = "name can't be empty")
    private String name;

    public ClothTypeDataForm() {
    }

    public ClothTypeDataForm(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
