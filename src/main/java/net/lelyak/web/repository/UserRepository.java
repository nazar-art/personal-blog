package net.lelyak.web.repository;

import net.lelyak.edu.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created by Nazar Lelyak.
 */
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUserName(String name);

    Optional<User> findByEmail(String email);
}
