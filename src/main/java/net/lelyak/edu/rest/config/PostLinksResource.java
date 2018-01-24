package net.lelyak.edu.rest.config;

import lombok.Getter;
import net.lelyak.edu.model.Post;
import net.lelyak.edu.rest.resources.CommentResource;
import net.lelyak.edu.rest.resources.PostResource;
import net.lelyak.edu.rest.resources.UserResource;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * @author Nazar Lelyak.
 */
public class PostLinksResource extends ResourceSupport {

    @Getter
    private final Post post;

    public PostLinksResource(Post post) {
        String userName = post.getUser().getUserName();
        long postId = post.getId();
        this.post = post;

        this.add(ControllerLinkBuilder.linkTo(methodOn(PostResource.class, postId)
                .getPost(postId)).withSelfRel());
        this.add(ControllerLinkBuilder.linkTo(methodOn(PostResource.class)
                .getAllPosts(userName)).withRel("posts-all"));

        this.add(ControllerLinkBuilder.linkTo(methodOn(UserResource.class)
                .getUser(userName)).withRel("post-user"));

        this.add(ControllerLinkBuilder.linkTo(methodOn(CommentResource.class)
                .findAllComments(postId)).withRel("post-all-comments"));
    }
}
