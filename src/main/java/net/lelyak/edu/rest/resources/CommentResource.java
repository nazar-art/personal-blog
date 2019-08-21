package net.lelyak.edu.rest.resources;

import lombok.AllArgsConstructor;
import net.lelyak.edu.model.Comment;
import net.lelyak.edu.rest.config.CommentLinksResource;
import net.lelyak.edu.rest.service.CommentService;
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
public class CommentResource {

    private final CommentService commentService;
    private final PostService postService;
    private final UserService userService;

    @GetMapping(value = "/users/{userId}/posts/{postId}/comments")
    public List<Comment> findAllComments(@PathVariable Long postId) {
        return commentService.findAllCommentsByPostId(postId);
    }

    @GetMapping(value = "/users/{userId}/posts/{postId}/comments/{id}")
    public CommentLinksResource getComment(@PathVariable Long id) {

        Comment comment = commentService.findComment(id);
        return new CommentLinksResource(comment);
    }

    @PostMapping(value = "/users/{userId}/posts/{postId}/comments")
    public void addComment(@PathVariable String userId, @PathVariable Long postId, @RequestBody Comment comment) {

        comment.setUser(userService.getUser(userId));
        comment.setPost(postService.findPost(postId));

        commentService.addComment(comment);
    }

    @PutMapping(value = "/users/{userId}/posts/{postId}/comments/{id}")
    public void updateComment(@PathVariable Long id, @RequestBody Comment comment) {
        commentService.updateComment(id, comment);
    }

    @DeleteMapping(value = "/users/{userId}/posts/{psotId}/comments/{id}")
    public void deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
    }

    @DeleteMapping(value = "/users/{userId}/posts/{postId}/comments")
    public void deleteAllComments(@PathVariable Long postId) {
        commentService.deleteAllCommentsByPostId(postId);
    }

}
