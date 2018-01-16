package net.lelyak.edu.rest.resources;

import lombok.AllArgsConstructor;
import net.lelyak.edu.model.Post;
import net.lelyak.edu.rest.config.PostLinksResource;
import net.lelyak.edu.rest.service.IPostService;
import net.lelyak.edu.rest.service.IUserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Nazar Lelyak.
 */
@RestController
@AllArgsConstructor
public class PostResource {

    private final IPostService postService;
    private final IUserService userService;

    @GetMapping(value = "/users/{userId}/posts")
    public List<Post> getAllPosts(@PathVariable String userId) {
        return postService.findAllPostsByUserName(userId);
    }

    @GetMapping(value = "/users/{userId}/posts/{id}")
    public PostLinksResource getPost(@PathVariable String userId, @PathVariable Long id) {
        Post post = postService.findPost(userId, id);
        return new PostLinksResource(post);
    }

    @PostMapping(value = "/users/{userId}/posts")
    public void addPost(@PathVariable String userId, @RequestBody Post post) {
        post.setUser(userService.getUser(userId));

        postService.addPost(post);
    }

    @PutMapping(value = "/users/{userId}/posts/{id}")
    public void updatePost(@PathVariable String userId, @PathVariable Long id, @RequestBody Post post) {
        postService.updatePost(userId, id, post);
    }

    @DeleteMapping(value = "/users/{userId}/posts/{id}")
    public void deletePost(@PathVariable String userId, @PathVariable Long id) {
        postService.deletePost(userId, id);
    }

    @DeleteMapping(value = "/users/{userId}/posts")
    public void deleteAllPosts(@PathVariable String userId) {
        postService.deleteAllPostsByUserName(userId);
    }

}