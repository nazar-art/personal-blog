package net.lelyak.edu.rest.service;

import net.lelyak.edu.model.BlogUser;

import java.util.List;

/**
 * @author Nazar Lelyak.
 */
public interface UserService {
    List<BlogUser> getAllUsers();

    BlogUser getUser(String userName);

    void createUser(BlogUser user);

    void updateUser(String userName, BlogUser user);

    void deleteUser(String userName);

    void deleteAllUsers();
}
