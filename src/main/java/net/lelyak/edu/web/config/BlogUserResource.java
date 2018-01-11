package net.lelyak.edu.web.config;

import lombok.Getter;
import net.lelyak.edu.model.BlogUser;
import net.lelyak.edu.web.resources.PostResource;
import net.lelyak.edu.web.resources.UserResource;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * @author Nazar Lelyak.
 */
public class BlogUserResource extends ResourceSupport {

    @Getter
    private final BlogUser user;

    public BlogUserResource(BlogUser user) {
        String userId = user.getUserName();
        this.user = user;

        this.add(ControllerLinkBuilder.linkTo(methodOn(UserResource.class, userId)
                .getUser(userId)).withSelfRel());
        this.add(ControllerLinkBuilder.linkTo(methodOn(UserResource.class)
                .getAllUsers()).withRel("users-all"));

        this.add(ControllerLinkBuilder.linkTo(methodOn(PostResource.class)
                .getAllPosts(userId)).withRel("user-posts"));
    }
}
