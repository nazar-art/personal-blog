package net.lelyak.edu.web.config;

import lombok.Getter;
import net.lelyak.edu.model.Comment;
import net.lelyak.edu.model.Post;
import net.lelyak.edu.web.resources.CommentResource;
import net.lelyak.edu.web.resources.PostResource;
import net.lelyak.edu.web.resources.UserResource;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * @author Nazar Lelyak.
 */
public class CommentLinksResource extends ResourceSupport {

    @Getter
    private final Comment comment;

    public CommentLinksResource(Comment comment) {
        long commentId = comment.getId();
        this.comment = comment;

        this.add(ControllerLinkBuilder.linkTo(methodOn(CommentResource.class, commentId)
                .getComment(commentId)).withSelfRel());
        this.add(ControllerLinkBuilder.linkTo(methodOn(UserResource.class)
                .getAllUsers()).withRel("comments-all"));
    }
}
