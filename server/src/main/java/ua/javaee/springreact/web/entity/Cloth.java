package ua.javaee.springreact.web.entity;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by kleba on 23.02.2019.
 */
@Entity
@Table
public class Cloth {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "cloth_id")
    private int clothId;
    @Column
    private String name;

    @OneToOne
    @JoinColumn(name = "sex_id")
    private Sex sex;

    @OneToOne
    @JoinColumn(name = "cloth_type_id")
    private ClothType clothType;

    public Cloth() {
    }

    ;

    public Cloth(String name, Sex sex, ClothType clothType) {
        this.name = name;
        this.sex = sex;
        this.clothType = clothType;
    }

    public int getClothId() {
        return clothId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public ClothType getClothType() {
        return clothType;
    }

    public void setClothType(ClothType clothType) {
        this.clothType = clothType;
    }
}
