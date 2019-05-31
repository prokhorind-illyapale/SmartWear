package ua.javaee.springreact.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.javaee.springreact.web.entity.LookType;

@Repository
public interface LookTypeRepository extends JpaRepository<LookType, Long> {

    @Query("SELECT lt From LookType lt where LOWER(lt.name) LIKE LOWER(concat(?1, '%'))")
    LookType findClothTypeByName(@Param("name") String name);
}
