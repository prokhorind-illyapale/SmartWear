package ua.javaee.springreact.web.form.lookforms;

/**
 * Created by kleba on 24.02.2019.
 */
public class LookTypeDataForm {

    private String name;

    public LookTypeDataForm() {
    }

    ;

    public LookTypeDataForm(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
