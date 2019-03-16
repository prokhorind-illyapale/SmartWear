package ua.javaee.springreact.web.data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by kleba on 24.02.2019.
 */
public class LookData {

    private UserData user;
    private String code;
    private int likes;
    private boolean isActive;
    private String description;
    private byte[] picture;
    private Set<LookTypeData> lookTypes = new HashSet<>();
    private Set<UserClothAttributeData> userClothAttributes = new HashSet<>();
    private List<CommentData> comments = new ArrayList<>();
    private BigDecimal minTemperature;
    private BigDecimal maxTemperature;

    public LookData() {
    }

    ;

    public LookData(UserData user, String code, int likes, boolean isActive, String description, Set<LookTypeData> lookTypes, Set<UserClothAttributeData> userClothAttributes, List<CommentData> comments, BigDecimal minTemperature, BigDecimal maxTemperature) {
        this.user = user;
        this.code = code;
        this.likes = likes;
        this.isActive = isActive;
        this.description = description;
        this.lookTypes = lookTypes;
        this.userClothAttributes = userClothAttributes;
        this.comments = comments;
        this.minTemperature = minTemperature;
        this.maxTemperature = maxTemperature;
    }

    public UserData getUser() {
        return user;
    }

    public void setUser(UserData user) {
        this.user = user;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<LookTypeData> getLookTypes() {
        return lookTypes;
    }

    public void setLookTypes(Set<LookTypeData> lookTypes) {
        this.lookTypes = lookTypes;
    }

    public Set<UserClothAttributeData> getUserClothAttributes() {
        return userClothAttributes;
    }

    public void setUserClothAttributes(Set<UserClothAttributeData> userClothAttributes) {
        this.userClothAttributes = userClothAttributes;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void setMinTemperature(BigDecimal minTemperature) {
        this.minTemperature = minTemperature;
    }

    public void setMaxTemperature(BigDecimal maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

    public BigDecimal getMinTemperature() {
        return minTemperature;
    }

    public BigDecimal getMaxTemperature() {
        return maxTemperature;
    }

    public void setComments(List<CommentData> comments) {
        this.comments = comments;
    }

    public List<CommentData> getComments() {
        return comments;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public byte[] getPicture() {
        return picture;
    }
}
