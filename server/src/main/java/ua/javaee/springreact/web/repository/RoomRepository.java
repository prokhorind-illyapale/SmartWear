package ua.javaee.springreact.web.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.javaee.springreact.web.entity.Room;

import java.util.List;

@Repository
public interface RoomRepository extends CrudRepository<Room, Long> {
    @Query(("SELECT r FROM Room r WHERE r.user.login= :login"))
    List<Room> findByLogin(String login);

    @Query("SELECT r FROM Room r WHERE r.user.login= :user AND r.roomName = :name ")
    Room findByRoomName(String name, String user);

}
