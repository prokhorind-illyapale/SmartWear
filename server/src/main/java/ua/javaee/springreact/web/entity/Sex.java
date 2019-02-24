package ua.javaee.springreact.web.entity;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by kleba on 23.02.2019.
 */
@Entity
@Table
public class Sex {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "sex_id")
    private int sexId;
    @Column
    private String name;

    public Sex() {
    }

    ;

    public Sex(String name) {
        this.name = name;
    }

    public int getSexId() {
        return sexId;
    }

    public void setSexId(int sexId) {
        this.sexId = sexId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
