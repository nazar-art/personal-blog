package net.lelyak.edu.web.repository;

import net.lelyak.edu.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Nazar Lelyak.
 */
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUserName(String name);

    Optional<User> findByEmail(String email);
}
