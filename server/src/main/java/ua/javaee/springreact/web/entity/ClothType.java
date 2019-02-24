package ua.javaee.springreact.web.entity;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by kleba on 23.02.2019.
 */
@Entity
@Table
public class ClothType {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "cloth_type_id")
    private int clothTypeId;
    @Column
    private String name;

    public ClothType() {
    }

    ;

    public ClothType(String name) {
        this.name = name;
    }

    public int getClothTypeId() {
        return clothTypeId;
    }

    public void setClothTypeId(int clothTypeId) {
        this.clothTypeId = clothTypeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
