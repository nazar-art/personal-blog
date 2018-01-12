package net.lelyak.edu.web.resources;

import lombok.AllArgsConstructor;
import net.lelyak.edu.model.Comment;
import net.lelyak.edu.web.config.CommentLinksResource;
import net.lelyak.edu.web.service.ICommentService;
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
public class CommentResource {

    private final ICommentService commentService;
    private final IPostService postService;
    private final IUserService userService;

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
