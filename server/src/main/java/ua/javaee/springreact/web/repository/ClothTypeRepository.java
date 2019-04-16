package ua.javaee.springreact.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.javaee.springreact.web.entity.ClothType;

/**
 * Created by kleba on 07.04.2019.
 */
@Repository
public interface ClothTypeRepository extends JpaRepository<ClothType, Long> {

    @Query("SELECT ct From ClothType ct where LOWER(ct.name) LIKE LOWER(concat(?1, '%'))")
    ClothType findClothTypeByName(@Param("name") String name);
}
