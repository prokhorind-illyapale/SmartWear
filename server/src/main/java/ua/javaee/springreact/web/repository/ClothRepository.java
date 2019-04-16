package ua.javaee.springreact.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.javaee.springreact.web.entity.Cloth;

/**
 * Created by kleba on 16.04.2019.
 */
@Repository
public interface ClothRepository extends JpaRepository<Cloth, Long> {
}
