package net.lelyak.edu.web.resources;

import lombok.AllArgsConstructor;
import net.lelyak.edu.model.Post;
import net.lelyak.edu.utils.converters.LocalDateTimeConverter;
import net.lelyak.edu.utils.exception.NotPresentedInDbException;
import net.lelyak.edu.web.config.PostLinksResource;
import net.lelyak.edu.web.service.IPostService;
import net.lelyak.edu.web.service.IUserService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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
    public PostLinksResource getPost(@PathVariable Long id) {
        Post post = postService.findPost(id);
        return new PostLinksResource(post);
    }

    @PostMapping(value = "/users/{userId}/posts")
    public void addPost(@PathVariable String userId, @RequestBody Post post) {
        post.setUser(userService.getUser(userId));

        postService.addPost(post);
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
