package net.lelyak.edu.web.repository;

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
    Optional<List<Comment>> findByUser(BlogUser user);
    Optional<List<Comment>> findByUser_UserName(String userName);

    Optional<List<Comment>> findByPost(Post post);
    List<Comment> findByPost_Id(Long id);

    Optional<List<Comment>> findByCreatedDate(LocalDateTime dateTime);
}
