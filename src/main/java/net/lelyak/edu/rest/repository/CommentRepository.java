package net.lelyak.edu.rest.repository;

import net.lelyak.edu.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Nazar Lelyak.
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost_Id(Long id);
}
