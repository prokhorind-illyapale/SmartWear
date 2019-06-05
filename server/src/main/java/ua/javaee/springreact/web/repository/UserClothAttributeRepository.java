package ua.javaee.springreact.web.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.javaee.springreact.web.entity.UserClothAttribute;

import java.util.List;

/**
 * Created by kleba on 24.04.2019.
 */
@Repository
public interface UserClothAttributeRepository extends JpaRepository<UserClothAttribute, Long> {

    @Query("SELECT uca FROM UserClothAttribute uca WHERE uca.cloth.name=:name")
    List<UserClothAttribute> findUserClothAttributeByCloth(@Param("name") String name);

    @Query("SELECT uca FROM UserClothAttribute uca WHERE uca.user.login=:name")
    List<UserClothAttribute> findAllByUserName(@Param("name") String name, Pageable pageable);

    @Query("SELECT uca FROM UserClothAttribute uca WHERE uca.code=:code")
    UserClothAttribute findUserClothAttributeByCode(@Param("code") Long code);

    @Modifying
    @Query("DELETE FROM UserClothAttribute uca WHERE uca.code=:code")
    void removeByCode(@Param("code") Long code);

    @Query("SELECT uca.code FROM UserClothAttribute uca WHERE id = (SELECT MAX(uca.userClothId) FROM UserClothAttribute uca)")
    Long getLastRow();
}
