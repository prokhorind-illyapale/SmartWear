package ua.javaee.springreact.web.form.lookforms;

import java.math.BigDecimal;

/**
 * Created by kleba on 24.02.2019.
 */
public class UserClothAttributeDataForm {

    private String userLogin;
    private long code;
    private String picture;
    private String description;
    private String size;
    private String color;
    private BigDecimal price;
    private ClothForm cloth;
    private boolean isPublic;

    public UserClothAttributeDataForm() {
    }

    ;

    public UserClothAttributeDataForm(String userLogin, boolean isPublic, long code, String picture, String description, String size, String color, BigDecimal price, ClothForm cloth) {
        this.userLogin = userLogin;
        this.code = code;
        this.picture = picture;
        this.description = description;
        this.size = size;
        this.color = color;
        this.price = price;
        this.cloth = cloth;
        this.isPublic = isPublic;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public long getCode() {
        return code;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getPicture() {
        return picture;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public ClothForm getCloth() {
        return cloth;
    }

    public void setCloth(ClothForm cloth) {
        this.cloth = cloth;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public boolean isPublic() {
        return isPublic;
    }
}
