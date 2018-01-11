package net.lelyak.edu.web.resources;

import lombok.AllArgsConstructor;
import net.lelyak.edu.model.BlogUser;
import net.lelyak.edu.web.service.IUserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Nazar Lelyak.
 */
@RestController
@AllArgsConstructor
public class UserResource {

    private IUserService userService;

    @GetMapping(value = "/users")
    public List<BlogUser> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping(value = "/users/{id}")
    public BlogUser getUser(@PathVariable String id) {
        return userService.getUser(id);
    }

    @PostMapping(value = "/users")
    public void addUser(@RequestBody BlogUser user) {
        userService.addUser(user);
    }

    @PutMapping(value = "/users/{id}")
    public void updateUser(@PathVariable String id, @RequestBody BlogUser user) {
        userService.updateUser(id, user);
    }

    @DeleteMapping(value = "/users/{id}")
    public void deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
    }

    @DeleteMapping(value = "/users")
    public void deleteAllUsers() {
        userService.deleteAllUsers();
    }

}
