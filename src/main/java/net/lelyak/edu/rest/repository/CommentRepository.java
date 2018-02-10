package net.lelyak.edu.rest.repository;

import net.lelyak.edu.model.Comment;
import net.lelyak.edu.model.Post;
import net.lelyak.edu.model.BlogUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @author Nazar Lelyak.
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost_Id(Long id);
}
