package net.lelyak.edu.web.service;

import net.lelyak.edu.model.Post;
import net.lelyak.edu.model.User;

import java.util.List;

/**
 * @author Nazar Lelyak.
 */
public interface IPostService {

    List<Post> findAllPostsByUserName(String userName);

    Post findPost(Long id);

    void addPost(Post post);

    void updatePost(Long id, Post post);

    void deletePost(Long id);

    void deleteAllPostsByUserName(String userName);
}
