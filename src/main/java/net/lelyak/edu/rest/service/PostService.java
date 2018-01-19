package net.lelyak.edu.rest.service;

import net.lelyak.edu.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author Nazar Lelyak.
 */
public interface PostService {

    List<Post> findAllPostsByUserName(String userName);

    Page<Post> listAllPostsByPage(String userName, Pageable pageable);

    Post findPost(String userName, Long id);

    void addPost(Post post);

    void updatePost(String userName, Long id, Post post);

    void deletePost(String userName, Long id);

    void deleteAllPostsByUserName(String userName);
}
