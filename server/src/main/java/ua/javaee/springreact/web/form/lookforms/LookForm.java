package ua.javaee.springreact.web.form.lookforms;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by kleba on 24.02.2019.
 */
public class LookForm {
    private String userLogin;
    private String code;
    private int likes;
    private boolean isPublic;
    private String description;
    private BigDecimal minTemperature;
    private BigDecimal maxTemperature;
    private Set<LookTypeDataForm> lookTypes = new HashSet<>();
    private Set<UserClothAttributeDataForm> userClothAttributes = new HashSet<>();

    public LookForm() {
    }

    public LookForm(String userLogin, String code, int likes, boolean isPublic, String description, BigDecimal minTemperature, BigDecimal maxTemperature, Set<LookTypeDataForm> lookTypes, Set<UserClothAttributeDataForm> userClothAttributes) {
        this.userLogin = userLogin;
        this.code = code;
        this.likes = likes;
        this.isPublic = isPublic;
        this.description = description;
        this.minTemperature = minTemperature;
        this.maxTemperature = maxTemperature;
        this.lookTypes = lookTypes;
        this.userClothAttributes = userClothAttributes;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public String getUserLogin() {
        return userLogin;
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

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<LookTypeDataForm> getLookTypes() {
        return lookTypes;
    }

    public void setLookTypes(Set<LookTypeDataForm> lookTypes) {
        this.lookTypes = lookTypes;
    }

    public Set<UserClothAttributeDataForm> getUserClothAttributes() {
        return userClothAttributes;
    }

    public void setUserClothAttributes(Set<UserClothAttributeDataForm> userClothAttributes) {
        this.userClothAttributes = userClothAttributes;
    }

    public void setMaxTemperature(BigDecimal maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

    public void setMinTemperature(BigDecimal minTemperature) {
        this.minTemperature = minTemperature;
    }

    public BigDecimal getMaxTemperature() {
        return maxTemperature;
    }

    public BigDecimal getMinTemperature() {
        return minTemperature;
    }
}
