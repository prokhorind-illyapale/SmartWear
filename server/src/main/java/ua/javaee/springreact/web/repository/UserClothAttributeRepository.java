package ua.javaee.springreact.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
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
}
