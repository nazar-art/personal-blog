package net.lelyak.web.service.impl;

import lombok.AllArgsConstructor;
import net.lelyak.edu.model.User;
import net.lelyak.web.repository.UserRepository;
import net.lelyak.web.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Nazar Lelyak.
 */
@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUser(String id) {
        return userRepository.findOne(id);
    }

    @Override
    public void addUser(User user) {

    }

    @Override
    public void updateUser(String id) {

    }

    @Override
    public void deleteUser(String id) {

    }
}
