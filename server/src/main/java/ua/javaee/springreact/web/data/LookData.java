package ua.javaee.springreact.web.data;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by kleba on 24.02.2019.
 */
public class LookData {

    private UserData user;
    private long code;
    private int likes;
    private boolean isActive;
    private String description;
    private Set<LookTypeData> lookTypes = new HashSet<>();
    private Set<UserClothAttributeData> userClothAttributes = new HashSet<>();
    private BigDecimal minTemperature;
    private BigDecimal maxTemperature;

    public LookData() {
    }

    ;

    public LookData(UserData user, long code, int likes, boolean isActive, String description, Set<LookTypeData> lookTypes, Set<UserClothAttributeData> userClothAttributes, BigDecimal minTemperature, BigDecimal maxTemperature) {
        this.user = user;
        this.code = code;
        this.likes = likes;
        this.isActive = isActive;
        this.description = description;
        this.lookTypes = lookTypes;
        this.userClothAttributes = userClothAttributes;
        this.minTemperature = minTemperature;
        this.maxTemperature = maxTemperature;
    }

    public UserData getUser() {
        return user;
    }

    public void setUser(UserData user) {
        this.user = user;
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
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
}
