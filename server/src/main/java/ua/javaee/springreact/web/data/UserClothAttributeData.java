package ua.javaee.springreact.web.data;

import java.math.BigDecimal;

/**
 * Created by kleba on 24.02.2019.
 */
public class UserClothAttributeData {

    private UserData userData;

    private long code;

    private byte[] picture;

    private String description;

    private String size;

    private String color;

    private BigDecimal price;

    private ClothData cloth;

    private boolean isPublic;

    public UserClothAttributeData(UserData userData, boolean isPublic, long code, byte[] picture, String description, String size, String color, BigDecimal price, ClothData cloth) {
        this.userData = userData;
        this.code = code;
        this.picture = picture;
        this.description = description;
        this.size = size;
        this.color = color;
        this.price = price;
        this.cloth = cloth;
        this.isPublic = isPublic;
    }

    public UserClothAttributeData() {

    }

    public UserData getUserData() {
        return userData;
    }

    public void setUserData(UserData userData) {
        this.userData = userData;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public long getCode() {
        return code;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
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

    public ClothData getCloth() {
        return cloth;
    }

    public void setCloth(ClothData cloth) {
        this.cloth = cloth;
    }

    public void setPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }

    public boolean isPublic() {
        return isPublic;
    }
}
