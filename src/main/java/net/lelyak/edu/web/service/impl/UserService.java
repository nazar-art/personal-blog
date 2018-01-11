package net.lelyak.edu.web.service.impl;

import lombok.AllArgsConstructor;
import net.lelyak.edu.model.User;
import net.lelyak.edu.utils.exception.DuplicateEmailException;
import net.lelyak.edu.utils.exception.UserNameIsNotUniqueException;
import net.lelyak.edu.utils.exception.NotPresentedInDbException;
import net.lelyak.edu.web.repository.UserRepository;
import net.lelyak.edu.web.service.IUserService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Nazar Lelyak.
 */
@Service
@AllArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;

    @Override
    public List<User> getAllUsers() {
        List<User> result = new ArrayList<>();
        result.addAll(userRepository.findAll());
        return result;
    }

    @Override
    public User getUser(String userName) {
        Assert.hasText(userName, "User name is empty");
        return userRepository.findByUserName(userName)
                .orElseThrow(() -> new NotPresentedInDbException(userName));
    }

    @Override
    public void addUser(User user) {
        Assert.notNull(user, "User is null");
        validateUserName(user.getUserName());
        validateUserEmail(user.getEmail());

        userRepository.save(user);
    }

    @Override
    public void updateUser(String userName, User user) {
        Assert.hasText(userName, "User name is empty");
        validateUserDBPresence(userName);
        userRepository.save(user);
    }

    @Override
    public void deleteUser(String userName) {
        Assert.hasText(userName, "User name is empty");
        validateUserDBPresence(userName);
        userRepository.delete(userName);
    }

    @Override
    public void deleteAllUsers() {
        getAllUsers()
                .forEach(user -> deleteUser(user.getUserName()));
    }




    private void validateUserName(String userName) {
        userRepository.findByUserName(userName)
                .ifPresent(s -> {
                    throw new UserNameIsNotUniqueException(userName);
                });
    }

    private void validateUserEmail(String email) {
        userRepository.findByEmail(email)
                .ifPresent(s -> {
                    throw new DuplicateEmailException(email);
                });
    }

    private void validateUserDBPresence(String name) {
        userRepository.findByUserName(name)
                .orElseThrow(() -> new NotPresentedInDbException(name));
    }
}
