package ua.javaee.springreact.web.form.lookforms;

/**
 * Created by kleba on 24.02.2019.
 */
public class ClothForm {

    private String name;
    private ClothTypeDataForm clothTypeData;
    private SexDataForm sex;

    public ClothForm() {
    }

    ;

    public ClothForm(String name, ClothTypeDataForm clothTypeData, SexDataForm sex) {
        this.name = name;
        this.clothTypeData = clothTypeData;
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ClothTypeDataForm getClothTypeData() {
        return clothTypeData;
    }

    public void setClothTypeData(ClothTypeDataForm clothTypeData) {
        this.clothTypeData = clothTypeData;
    }

    public SexDataForm getSex() {
        return sex;
    }

    public void setSex(SexDataForm sex) {
        this.sex = sex;
    }
}
