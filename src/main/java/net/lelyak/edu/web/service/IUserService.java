package net.lelyak.edu.web.service;

import net.lelyak.edu.model.User;

import java.util.List;

/**
 * @author Nazar Lelyak.
 */
public interface IUserService {
    List<User> getAllUsers();

    User getUser(String userName);

    void addUser(User user);

    void updateUser(String userName, User user);

    void deleteUser(String userName);

    void deleteAllUsers();
}
