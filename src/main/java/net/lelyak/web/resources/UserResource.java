package net.lelyak.web.resources;

import net.lelyak.edu.model.User;
import net.lelyak.web.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Nazar Lelyak.
 */
@RestController
public class UserResource {

    @Autowired
    private IUserService userService;

    @GetMapping(value = "/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }


}
