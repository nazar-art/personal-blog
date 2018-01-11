package net.lelyak.edu.web.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import net.lelyak.edu.model.BlogUser;
import net.lelyak.edu.model.Post;
import net.lelyak.edu.utils.exception.DuplicateEmailException;
import net.lelyak.edu.utils.exception.DuplicateUserNameException;
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
    public void addUser(BlogUser user) {
        Assert.notNull(user, "User is null");
        validateUserName(user.getUserName());
        validateUserEmail(user.getEmail());

        userRepository.save(user);
    }

    @Override
    public void updateUser(String userName, BlogUser user) {
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

    @SneakyThrows
    public static void main(String[] args) {
        BlogUser user = BlogUser.builder().userName("johny").email("johny@gmail.com").password("johny123").build();

        Post first_post = Post.builder().postText("First post").build();

        ObjectMapper mapper = new ObjectMapper();
        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(user));

        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(first_post));
    }
}
