package net.lelyak.edu.rest.resources;

import lombok.AllArgsConstructor;
import net.lelyak.edu.model.BlogUser;
import net.lelyak.edu.rest.config.BlogUserResource;
import net.lelyak.edu.rest.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Nazar Lelyak.
 */
@RestController
@AllArgsConstructor
public class UserResource {

    private UserService userService;

    @GetMapping(value = "/users")
    public List<BlogUser> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping(value = "/users/{id}")
    public BlogUserResource getUser(@PathVariable String id) {
        BlogUser user = userService.getUser(id);
        return new BlogUserResource(user);
    }

    @PostMapping(value = "/users")
    public BlogUser addUser(@RequestBody BlogUser user) {
        return userService.createUser(user);
    }

    @PutMapping(value = "/users/{id}")
    public void updateUser(@PathVariable String id, @RequestBody BlogUser user) {
        userService.updateUser(id, user);
    }

}
