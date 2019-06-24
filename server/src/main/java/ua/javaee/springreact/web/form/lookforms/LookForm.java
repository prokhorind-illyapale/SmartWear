package ua.javaee.springreact.web.form.lookforms;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by kleba on 24.02.2019.
 */
public class LookForm {
    private String userLogin;
    private long code;
    private int likes;
    private boolean isPublic;
    private String description;
    private BigDecimal minTemperature;
    private BigDecimal maxTemperature;
    private byte[] picture;
    private Set<LookTypeDataForm> lookTypes = new HashSet<>();
    private Set<Long> userClothAttributesCodes = new HashSet<>();
    private List<CommentForm> comments = new ArrayList<>();
    private Set<String> likedUsers = new HashSet<>();

    public LookForm() {
    }

    public LookForm(String userLogin,long code, int likes, boolean isPublic, String description, BigDecimal minTemperature, BigDecimal maxTemperature, Set<LookTypeDataForm> lookTypes, Set<UserClothAttributeDataForm> userClothAttributes, List<CommentForm> comments) {
        this.userLogin = userLogin;
        this.code = code;
        this.likes = likes;
        this.isPublic = isPublic;
        this.description = description;
        this.minTemperature = minTemperature;
        this.maxTemperature = maxTemperature;
        this.lookTypes = lookTypes;
        this.userClothAttributesCodes = userClothAttributes.stream().map(UserClothAttributeDataForm::getCode).collect(Collectors.toSet());
        this.comments = comments;
    }

    public LookForm(boolean isPublic, String description, BigDecimal minTemperature, BigDecimal maxTemperature, Set<LookTypeDataForm> lookTypes, Set<Long> userClothAttributesCodes) {
        this.isPublic = isPublic;
        this.description = description;
        this.minTemperature = minTemperature;
        this.maxTemperature = maxTemperature;
        this.lookTypes = lookTypes;
        this.userClothAttributesCodes = userClothAttributesCodes;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public long getCode() {
        return code;
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

    public Set<Long> getUserClothAttributes() {
        return userClothAttributesCodes;
    }

    public void setUserClothAttributes(Set<UserClothAttributeDataForm> userClothAttributes) {
        this.userClothAttributesCodes = userClothAttributes.stream().map(UserClothAttributeDataForm::getCode).collect(Collectors.toSet());
    }

    public void setUserClothAttributesCodes(Set<Long> userClothAttributesCodes) {
        this.userClothAttributesCodes = userClothAttributesCodes;
    }

    public Set<Long> getUserClothAttributesCodes() {
        return userClothAttributesCodes;
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

    public void setComments(List<CommentForm> comments) {
        this.comments = comments;
    }

    public List<CommentForm> getComments() {
        return comments;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setLikedUsers(Set<String> likedUsers) {
        this.likedUsers = likedUsers;
    }

    public Set<String> getLikedUsers() {
        return likedUsers;
    }
}
