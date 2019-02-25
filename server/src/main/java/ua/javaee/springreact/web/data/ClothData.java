package ua.javaee.springreact.web.data;

/**
 * Created by kleba on 24.02.2019.
 */
public class ClothData {

    private String name;

    private ClothTypeData clothTypeData;

    private SexData sex;

    public ClothData() {
    }

    public ClothData(String name, ClothTypeData clothTypeData, SexData sex) {
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

    public ClothTypeData getClothTypeData() {
        return clothTypeData;
    }

    public void setClothTypeData(ClothTypeData clothTypeData) {
        this.clothTypeData = clothTypeData;
    }

    public SexData getSex() {
        return sex;
    }

    public void setSex(SexData sex) {
        this.sex = sex;
    }
}
