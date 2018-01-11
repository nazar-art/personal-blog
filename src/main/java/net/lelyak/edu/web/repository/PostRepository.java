package net.lelyak.edu.web.repository;

import net.lelyak.edu.model.Comment;
import net.lelyak.edu.model.Post;
import net.lelyak.edu.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @author Nazar Lelyak.
 */
public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<List<Post>> findByUser(User user);
    List<Post> findByUser_UserName(String userName);

    Optional<List<Post>> findByCreatedDate(LocalDateTime dateTime);
}
