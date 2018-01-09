package net.lelyak.web.service;

import net.lelyak.edu.model.User;

import java.util.List;

/**
 * Created by Nazar Lelyak.
 */
public interface IUserService {
    List<User> getAllUsers();

    User getUser(String id);

    void addUser(User user);

    void updateUser(String id, User user);

    void deleteUser(String id);
}
