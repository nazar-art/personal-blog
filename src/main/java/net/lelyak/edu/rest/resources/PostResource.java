package net.lelyak.edu.rest.resources;

import lombok.AllArgsConstructor;
import net.lelyak.edu.model.Post;
import net.lelyak.edu.rest.config.PostLinksResource;
import net.lelyak.edu.rest.service.PostService;
import net.lelyak.edu.rest.service.UserService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Nazar Lelyak.
 */
@RestController
@AllArgsConstructor
public class PostResource {

    private final PostService postService;
    private final UserService userService;

    @GetMapping(value = "/users/{userId}/posts")
    public List<Post> getAllPosts(@PathVariable String userId) {
        return postService.findAllPostsByUserName(userId);
    }

    @GetMapping(value = "/users/{userId}/posts/{id}")
    public PostLinksResource getPost(@PathVariable Long id) {
        Post post = postService.findPost(id);
        return new PostLinksResource(post);
    }

    @PostMapping(value = "/users/{userId}/posts")
    public void addPost(@PathVariable String userId, @RequestBody Post post) {
        post.setUser(userService.getUser(userId));

        postService.createPost(post);
    }

    @PutMapping(value = "/users/{userId}/posts/{id}")
    public void updatePost(@PathVariable Long id, @RequestBody Post post) {
        postService.updatePost(id, post);
    }

    @DeleteMapping(value = "/users/{userId}/posts/{id}")
    public void deletePost(@PathVariable Long id) {
        postService.deletePost(id);
    }

    @DeleteMapping(value = "/users/{userId}/posts")
    public void deleteAllPosts(@PathVariable String userId) {
        postService.deleteAllPostsByUserName(userId);
    }

}
