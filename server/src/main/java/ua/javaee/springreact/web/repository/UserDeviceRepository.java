package ua.javaee.springreact.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ua.javaee.springreact.web.entity.UserDevice;

import java.util.List;

@Repository
public interface UserDeviceRepository extends JpaRepository<UserDevice, Long> {
    @Query("SELECT ud FROM UserDevice ud WHERE ud.user.login= :login")
    List<UserDevice> findAllByByUserLogin(String login);

    @Query("SELECT ud FROM UserDevice ud WHERE ud.user.login= :login AND ud.name = :userDeviceName")
    UserDevice findByUserLoginAndName(String login, String userDeviceName);

    @Query("SELECT ud FROM UserDevice ud WHERE ud.user.login= :login AND ud.room.roomName = :roomName")
    List<UserDevice> findAllByUserLoginAndRoomName(String login, String roomName);

    @Query("SELECT ud.userDeviceId FROM UserDevice ud WHERE ud.user.login= :login AND ud.room.roomName = :roomName")
    List<Long> findUserDeviceIdsInARoom(String login, String roomName);

    @Query("SELECT DISTINCT ud.valueType FROM UserDevice ud WHERE ud.room.roomId IN(:ids)")
    List<String> findAllIndicatorTypesInRooms(List<Long> ids);

    @Query("SELECT ud.userDeviceId FROM UserDevice ud WHERE ud.user.login= :login AND ud.valueType = :valueType")
    List<Long> findUserDeviceIdsInARoomByType(String login, String valueType);
}
