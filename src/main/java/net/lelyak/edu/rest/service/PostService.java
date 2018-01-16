package net.lelyak.edu.rest.service;

import net.lelyak.edu.model.Post;

import java.util.List;

/**
 * @author Nazar Lelyak.
 */
public interface PostService {

    List<Post> findAllPostsByUserName(String userName);

    Post findPost(String userName, Long id);

    void addPost(Post post);

    void updatePost(String userName, Long id, Post post);

    void deletePost(String userName, Long id);

    void deleteAllPostsByUserName(String userName);
}
