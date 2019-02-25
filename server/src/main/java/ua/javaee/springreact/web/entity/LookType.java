package ua.javaee.springreact.web.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by kleba on 23.02.2019.
 */
@Table
@Entity
public class LookType {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "look_type_id")
    private int lookTypeId;
    @Column
    private String name;

    @ManyToMany(mappedBy = "lookTypes")
    private Set<Look> looks = new HashSet<>();

    public LookType() {
    }

    ;

    public LookType(String name, Set<Look> looks) {
        this.name = name;
        this.looks = looks;
    }

    public int getLookTypeId() {
        return lookTypeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLooks(Set<Look> looks) {
        this.looks = looks;
    }

    public Set<Look> getLooks() {
        return looks;
    }
}
