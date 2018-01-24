package net.lelyak.edu.rest.repository;

import net.lelyak.edu.model.BlogUser;
import net.lelyak.edu.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @author Nazar Lelyak.
 */
public interface PostRepository extends CrudRepository<Post, Long> {

    List<Post> findByUser(BlogUser user);

//    List<Post> findByUser_UserName(String userName);

    Page<Post> findByUser_UserName(String userId, Pageable pageable);

    Optional<List<Post>> findByCreatedDate(LocalDateTime dateTime);
}
