package ua.javaee.springreact.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.javaee.springreact.web.entity.Sex;

@Repository
public interface SexRepository extends JpaRepository<Sex, Long> {

    @Query("SELECT s From Sex s where s.name=:name")
    Sex getSexByName(@Param("name") String name);
}
