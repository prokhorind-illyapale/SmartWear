package ua.javaee.springreact.web.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by kleba on 23.02.2019.
 */
@Table(name = "UserClothAttribute")
@Entity
public class UserClothAttribute {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "user_cloth_id")
    private long userClothId;
    @Column(unique = true)
    private long code;
    @Column
    private byte[] picture;
    @Column
    private String description;
    @Column
    private String size;
    @Column
    private String color;
    @Column
    private BigDecimal price;
    @Column
    private boolean isPublic;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "cloth_id")
    private Cloth cloth;

    @ManyToMany(mappedBy = "userClothAttributes")
    private Set<Look> looks = new HashSet<>();

    public UserClothAttribute() {
    }

    ;

    public UserClothAttribute(long code, boolean isPublic, byte[] picture, String description, String size, String color, BigDecimal price, User user, Cloth cloth, Set<Look> looks) {
        this.code = code;
        this.picture = picture;
        this.isPublic = isPublic;
        this.description = description;
        this.size = size;
        this.color = color;
        this.price = price;
        this.user = user;
        this.cloth = cloth;
        this.looks = looks;
    }

    public void setUserClothId(long userClothId) {
        this.userClothId = userClothId;
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

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setCloth(Cloth cloth) {
        this.cloth = cloth;
    }

    public Cloth getCloth() {
        return cloth;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public long getCode() {
        return code;
    }

    public void setLooks(Set<Look> looks) {
        this.looks = looks;
    }

    public Set<Look> getLooks() {
        return looks;
    }

    public long getUserClothId() {
        return userClothId;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public boolean isPublic() {
        return isPublic;
    }
}
