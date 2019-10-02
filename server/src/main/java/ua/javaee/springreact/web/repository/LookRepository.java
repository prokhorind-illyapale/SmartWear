package ua.javaee.springreact.web.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ua.javaee.springreact.web.entity.Look;

import java.util.List;

/**
 * Created by kleba on 24.02.2019.
 */
@Repository
public interface LookRepository extends JpaRepository<Look, Long> {

    Look findByCode(long code);

    @Query(("SELECT k FROM Look k WHERE k.user.login= :login"))
    List<Look> findAllUserLooks(String login);

    @Query("SELECT k FROM Look k WHERE k.user.login= :login AND :minTemp >= k.minTemperature   AND  :maxTemp  <= k.maxTemperature")
    List<Look> findMostPopularSessionUserLooks(String login, int minTemp, int maxTemp, Pageable pageable);

    @Query("SELECT k FROM Look k WHERE :minTemp >= k.minTemperature  AND  k.user.login != :login AND :maxTemp  <= k.maxTemperature AND k.isActive = true AND k.user.sex=:sex")
    List<Look> findMostPopularUserLooks(String login, int minTemp, int maxTemp, String sex, Pageable pageable);


    @Query("SELECT k FROM Look k  JOIN UserClothAttribute uca ON uca.code IN (:userAttributesCodes)")
    List<Look> findAllLooksByUserAttributes(List<Long> userAttributesCodes);

    @Query("SELECT k.isActive FROM Look k WHERE k.code = :code")
    Boolean isLookPublic(long code);

    @Query("SELECT k FROM Look k WHERE k.code = :code AND k.user.login = :login")
    Look isPrincipalLook(long code, String login);

    @Modifying
    @Query("DELETE FROM Look k WHERE k.code = :code")
    void deleteLookByCode(long code);

    @Query("SELECT k FROM Look k WHERE  k.user.login = :login")
    List<Look> getLooksByLogin(String login);

    @Query("SELECT l.lookId FROM Look l WHERE id = (SELECT MAX(l.lookId) FROM Look l)")
    Long getLastRow();
}
