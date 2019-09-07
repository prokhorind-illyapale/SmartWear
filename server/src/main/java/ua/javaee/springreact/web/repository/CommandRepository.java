package ua.javaee.springreact.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.javaee.springreact.web.entity.Command;

@Repository
public interface CommandRepository extends JpaRepository<Command, Long> {
    Command findByName(String name);
}
