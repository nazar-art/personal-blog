package net.lelyak.edu.rest.resources;

import lombok.AllArgsConstructor;
import net.lelyak.edu.model.BlogUser;
import net.lelyak.edu.rest.config.BlogUserResource;
import net.lelyak.edu.rest.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Nazar Lelyak.
 */
@RestController
@AllArgsConstructor
public class UserResource {

    private UserService userService;

    @GetMapping(value = "v1/users")
    public List<BlogUser> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping(value = "v1/users/{id}")
    public BlogUserResource getUser(@PathVariable String id) {
        BlogUser user = userService.getUser(id);
        return new BlogUserResource(user);
    }

    @PostMapping(value = "v1/users")
    public void addUser(@RequestBody BlogUser user) {
        userService.createUser(user);
    }

    @PutMapping(value = "v1/users/{id}")
    public void updateUser(@PathVariable String id, @RequestBody BlogUser user) {
        userService.updateUser(id, user);
    }

}
