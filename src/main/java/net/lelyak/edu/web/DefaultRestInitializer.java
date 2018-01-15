package net.lelyak.edu.web;

import lombok.AllArgsConstructor;
import net.lelyak.edu.model.BlogUser;
import net.lelyak.edu.model.Comment;
import net.lelyak.edu.model.Post;
import net.lelyak.edu.rest.service.impl.CommentService;
import net.lelyak.edu.rest.service.impl.PostService;
import net.lelyak.edu.rest.service.impl.UserService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @author Nazar Lelyak.
 */
@Component
@AllArgsConstructor
public class DefaultRestInitializer implements ApplicationRunner {

    private final UserService userService;
    private final PostService postService;
    private final CommentService commentService;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        BlogUser carlos = BlogUser.builder()
                .userName("carlos")
                .password("secret")
                .email("carlos@gmail.com")
                .build();

        userService.createUser(carlos);

        Post firstPost = Post.builder()
                .postText("First post !!! UUUUUhuuuuu!")
                .user(carlos)
                .build();
        Post secondPost = Post.builder()
                .postText("I like this blog posting so much :)")
                .user(carlos)
                .build();
        Post thirdPost = Post.builder()
                .postText("To be or not to be? What is the question.")
                .user(carlos)
                .build();

        postService.addPost(firstPost);
        postService.addPost(secondPost);
        postService.addPost(thirdPost);

        BlogUser sailor = BlogUser.builder()
                .userName("sailor").password("123").email("sailor@gmail.com").build();
        userService.createUser(sailor);

        Comment commentToFirstPost = Comment.builder().commentText("you are an idiot!")
                .user(sailor).post(firstPost).build();
        Comment secondCommentToFirstPost = Comment.builder().commentText("You should sail to Antarctica!")
                .user(sailor).post(firstPost).build();
        Comment commentToSecondPost = Comment.builder().commentText("Shut up!")
                .user(sailor).post(secondPost).build();
        Comment commentToThirdPost = Comment.builder().commentText("You need to go and eat spinach! As much as you can!")
                .user(sailor).post(thirdPost).build();

        commentService.addComment(commentToFirstPost);
        commentService.addComment(secondCommentToFirstPost);
        commentService.addComment(commentToSecondPost);
        commentService.addComment(commentToThirdPost);
    }
}
