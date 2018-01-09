package net.lelyak.web.repository;

import net.lelyak.edu.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Nazar Lelyak.
 */
public interface UserRepository extends JpaRepository<User, String> {

}
