package ua.javaee.springreact.web.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by kleba on 23.02.2019.
 */
@Table
@Entity
public class Look {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "look_id")
    private long lookId;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Column(unique = true)
    private String code;
    @Column
    private int likes;
    @Column
    private String description;
    @Column
    private boolean isActive;
    @Column
    private BigDecimal minTemperature;
    @Column
    private BigDecimal maxTemperature;

    @OneToMany(mappedBy = "look", cascade = CascadeType.REMOVE)
    private List<Comment> comments = new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "Look_To_Look_type",
            joinColumns = {@JoinColumn(name = "look_id")},
            inverseJoinColumns = {@JoinColumn(name = "look_type_id")}
    )
    private Set<LookType> lookTypes = new HashSet<>();

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "Look_To_User_Clothes",
            joinColumns = {@JoinColumn(name = "look_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_cloth_id")}
    )
    private Set<UserClothAttribute> userClothAttributes = new HashSet<>();

    public Look() {
    }

    ;

    public Look(User user, String code, int likes, String description, boolean isActive, BigDecimal minTemperature, BigDecimal maxTemperature, List<Comment> comments, Set<LookType> lookTypes, Set<UserClothAttribute> userClothAttributes) {
        this.user = user;
        this.code = code;
        this.likes = likes;
        this.description = description;
        this.isActive = isActive;
        this.minTemperature = minTemperature;
        this.maxTemperature = maxTemperature;
        this.comments = comments;
        this.lookTypes = lookTypes;
        this.userClothAttributes = userClothAttributes;
    }

    public long getLookId() {
        return lookId;
    }

    public void setLookId(long lookId) {
        this.lookId = lookId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
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

    public Set<LookType> getLookTypes() {
        return lookTypes;
    }

    public void setLookTypes(Set<LookType> lookTypes) {
        this.lookTypes = lookTypes;
    }

    public Set<UserClothAttribute> getUserClothAttributes() {
        return userClothAttributes;
    }

    public void setUserClothAttributes(Set<UserClothAttribute> userClothAttributes) {
        this.userClothAttributes = userClothAttributes;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isActive() {
        return isActive;
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

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Comment> getComments() {
        return comments;
    }
}