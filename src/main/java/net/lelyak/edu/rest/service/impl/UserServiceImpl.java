package net.lelyak.edu.rest.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.lelyak.edu.model.BlogUser;
import net.lelyak.edu.model.Role;
import net.lelyak.edu.rest.repository.CommentRepository;
import net.lelyak.edu.rest.repository.PostRepository;
import net.lelyak.edu.rest.repository.UserRepository;
import net.lelyak.edu.rest.service.UserService;
import net.lelyak.edu.utils.exception.DuplicateEmailException;
import net.lelyak.edu.utils.exception.DuplicateUserNameException;
import net.lelyak.edu.utils.exception.NotPresentedInDbException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @author Nazar Lelyak.
 */
@Slf4j
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public List<BlogUser> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public BlogUser getUser(String userName) {
        Assert.hasText(userName, "User name is empty");
        return userRepository.findByUserName(userName)
                .orElseThrow(() -> new NotPresentedInDbException(userName));
    }

    @Override
    @Transactional
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
    @Transactional
    public void updateUser(String userName, BlogUser user) {
        Assert.hasText(userName, "User name is empty");
        validateUserDBPresence(userName);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteUser(String userId) {
        validateUserDBPresence(userId);

        // delete all comments and all posts which belongs to user -> after it user itself
        postRepository.findByUser_UserName(userId).forEach(p -> {
            commentRepository.findByPost_Id(p.getId())
                    .forEach(c -> commentRepository.delete(c.getId()));
            postRepository.delete(p.getId());
        });

        userRepository.delete(userId);
        log.debug("DELETED_USER: {}", userId);
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

    private void validateUserDBPresence(String name) {
        userRepository.findByUserName(name)
                .orElseThrow(() -> new NotPresentedInDbException(name));
    }
}
