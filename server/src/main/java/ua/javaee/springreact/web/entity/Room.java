package ua.javaee.springreact.web.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Table
@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "room_id")
    private long roomId;
    @Column
    private String roomName;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @OneToMany(mappedBy = "room", cascade = CascadeType.REMOVE)
    private List<UserDevice> userDevices = new ArrayList<>();

    public Room() {

    }

    public long getRoomId() {
        return roomId;
    }

    public void setRoomId(long roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUserDevices(List<UserDevice> userDevices) {
        this.userDevices = userDevices;
    }

    public List<UserDevice> getUserDevices() {
        return userDevices;
    }
}
