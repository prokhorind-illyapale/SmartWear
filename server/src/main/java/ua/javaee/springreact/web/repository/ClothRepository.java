package ua.javaee.springreact.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ua.javaee.springreact.web.entity.Cloth;

/**
 * Created by kleba on 16.04.2019.
 */
@Repository
public interface ClothRepository extends JpaRepository<Cloth, Long> {

    @Query("SELECT c FROM Cloth c where c.name = :clothName AND c.sex.name = :sexType")
    Cloth findCloth(String clothName, String sexType);
}
