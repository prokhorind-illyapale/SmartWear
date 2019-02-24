package ua.javaee.springreact.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ua.javaee.springreact.web.entity.Comment;

import java.util.List;

/**
 * Created by kleba on 24.02.2019.
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("SELECT c FROM Comment c WHERE c.look.code = :code")
    List<Comment> findCommentsByLookCode(String code);
}
