package ua.javaee.springreact.web.repository;

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

    Look findByCode(String code);

    @Query(("SELECT k FROM Look k WHERE k.user.login= :login"))
    List<Look> findAllUserLooks(String login);

    @Query("SELECT k FROM Look k  JOIN UserClothAttribute uca ON uca.code IN (:userAttributesCodes)")
    List<Look> findAllLooksByUserAttributes(List<String> userAttributesCodes);

    @Query("SELECT k.isActive FROM Look k WHERE k.code = :code")
    Boolean isLookPublic(String code);

    @Query("SELECT k FROM Look k WHERE k.code = :code AND k.user.login = :login")
    Look isPrincipalLook(String code, String login);

    @Modifying
    @Query("DELETE FROM Look k WHERE k.code = :code")
    void deleteLookByCode(String code);

    @Query("SELECT k FROM Look k WHERE  k.user.login = :login")
    List<Look> getLooksByLogin(String login);
}
