package ua.javaee.springreact.web.entity;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by kleba on 06.02.2019.
 */
@Entity
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy= IDENTITY)
    @Column(name = "role_id")
    private int roleId;
    @Column(unique = true)
    private String name;
    @OneToMany(mappedBy = "role" ,cascade=CascadeType.ALL)
    private List<User> users = new ArrayList<>();

    public Role(){}

    public Role(String name, List<User> users) {
        this.name = name;
        this.users = users;
    }
    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getRoleId() {
        return roleId;
    }
    public String getName() {
        return name;
    }
    public void setUsers(List<User> users) {
        this.users = users;
    }
    public List<User> getUsers() {
        return users;
    }
}
