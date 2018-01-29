package net.lelyak.edu.rest.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.lelyak.edu.model.BlogUser;
import net.lelyak.edu.model.Role;
import net.lelyak.edu.rest.repository.UserRepository;
import net.lelyak.edu.rest.service.UserService;
import net.lelyak.edu.utils.exception.DuplicateEmailException;
import net.lelyak.edu.utils.exception.DuplicateUserNameException;
import net.lelyak.edu.utils.exception.NotPresentedInDbException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Nazar Lelyak.
 */
@Slf4j
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<BlogUser> getAllUsers() {
        List<BlogUser> result = new ArrayList<>();
        result.addAll(userRepository.findAll());
        return result;
    }

    @Override
    public BlogUser getUser(String userName) {
        Assert.hasText(userName, "User name is empty");
        return userRepository.findByUserName(userName)
                .orElseThrow(() -> new NotPresentedInDbException(userName));
    }

    @Override
    public BlogUser createUser(BlogUser user) {
        log.debug("Create new user is called: {}", user);
        Assert.notNull(user, "User is null");

        validateUserName(user.getUserName());
        validateUserEmail(user.getEmail());

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER);
        user.setEnabled(Boolean.TRUE);

        BlogUser createdUser = userRepository.save(user);
        log.debug("Created new user: {}", user);
        return createdUser;
    }

    @Override
    public void updateUser(String userName, BlogUser user) {
        Assert.hasText(userName, "User name is empty");
        validateUserDBPresence(userName);
        userRepository.save(user);
    }

    @Override // todo add transactional method for deleting all posts + comments
    public void deleteUser(String userId) {
        validateUserDBPresence(userId);

        userRepository.delete(userId);
        log.debug("DELETE_USER: {}", userId);
    }


    private void validateUserName(String userName) {
        userRepository.findByUserName(userName)
                .ifPresent(s -> {
                    throw new DuplicateUserNameException(userName);
                });
    }

    private void validateUserEmail(String email) {
        userRepository.findByEmail(email)
                .ifPresent(s -> {
                    throw new DuplicateEmailException(email);
                });
    }

    public void validateUserDBPresence(String name) {
        userRepository.findByUserName(name)
                .orElseThrow(() -> new NotPresentedInDbException(name));
    }
}
