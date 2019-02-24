package ua.javaee.springreact.web.form.lookforms;

import ua.javaee.springreact.web.data.UserData;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by kleba on 24.02.2019.
 */
public class LookForm {
    private UserData user;
    private long code;
    private int likes;
    private boolean isPublic;
    private String description;
    private Set<LookTypeDataForm> lookTypes = new HashSet<>();
    private Set<UserClothAttributeDataForm> userClothAttributes = new HashSet<>();

    public LookForm() {
    }

    public LookForm(UserData user, long code, int likes, boolean isPublic, String description, Set<LookTypeDataForm> lookTypes, Set<UserClothAttributeDataForm> userClothAttributes) {
        this.user = user;
        this.code = code;
        this.likes = likes;
        this.isPublic = isPublic;
        this.description = description;
        this.lookTypes = lookTypes;
        this.userClothAttributes = userClothAttributes;
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
}
