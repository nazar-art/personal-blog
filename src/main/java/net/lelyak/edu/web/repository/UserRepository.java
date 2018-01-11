package net.lelyak.edu.web.repository;

import net.lelyak.edu.model.BlogUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Nazar Lelyak.
 */
public interface UserRepository extends JpaRepository<BlogUser, String> {
    Optional<BlogUser> findByUserName(String name);

    Optional<BlogUser> findByEmail(String email);
}
