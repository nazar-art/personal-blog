package net.lelyak.web.service.impl;

import net.lelyak.edu.model.User;
import net.lelyak.edu.utils.exception.UserIsNotUniqueException;
import net.lelyak.edu.utils.exception.UserNotFoundException;
import net.lelyak.web.repository.UserRepository;
import net.lelyak.web.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        List<User> result = new ArrayList<>();
        result.addAll(userRepository.findAll());
        return result;
    }

    @Override
    public User getUser(String id) {
        return userRepository.findOne(id);
    }

    @Override
    public void addUser(User user) {
        validateUserName(user.getUserName());
        userRepository.save(user);
    }

    @Override
    public void updateUser(String id, User user) {
        validateUserDBPresence(id);
        userRepository.save(user);
    }

    @Override
    public void deleteUser(String id) {
        validateUserDBPresence(id);
        userRepository.delete(id);
    }


    private void validateUserName(String userName) {
        userRepository.findByUserName(userName)
                .ifPresent(s -> {
                    throw new UserIsNotUniqueException(userName);
                });
    }

    private void validateUserDBPresence(String name) {
        userRepository.findByUserName(name)
                .orElseThrow(() -> new UserNotFoundException(name));
    }
}
