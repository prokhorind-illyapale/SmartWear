package ua.javaee.springreact.web.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by kleba on 06.02.2019.
 */
@Table(name = "user")
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "user_id")
    private int userId;
    @Column
    private String email;
    @Column(unique = true)
    private String login;
    @Column
    private String sex;
    @Column
    private String password;
    @Column
    private String city;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "role_id")
    private Role role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Look> looks = new ArrayList();
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Room> rooms = new ArrayList<>();
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<UserDevice> userDevices = new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "User_To_Look_Likes",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "look_id")}
    )
    private Set<Look> lookLikes = new HashSet<>();

    public User() {
    }

    public User(String email, String login, String sex, String password, String city, Role role, List<Look> looks) {
        this.email = email;
        this.login = login;
        this.sex = sex;
        this.password = password;
        this.city = city;
        this.role = role;
        this.looks = looks;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public Role getRole() {
        return role;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSex() {
        return sex;
    }

    public void setLooks(List<Look> looks) {
        this.looks = looks;
    }

    public List<Look> getLooks() {
        return looks;
    }

    public void setLookLikes(Set<Look> lookLikes) {
        this.lookLikes = lookLikes;
    }

    public Set<Look> getLookLikes() {
        return lookLikes;
    }

    public void setUserDevices(List<UserDevice> userDevices) {
        this.userDevices = userDevices;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public List<UserDevice> getUserDevices() {
        return userDevices;
    }

    public List<Room> getRooms() {
        return rooms;
    }
}
