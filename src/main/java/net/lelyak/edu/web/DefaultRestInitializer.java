package net.lelyak.edu.web;

import lombok.AllArgsConstructor;
import net.lelyak.edu.model.BlogUser;
import net.lelyak.edu.model.Comment;
import net.lelyak.edu.model.Post;
import net.lelyak.edu.model.Role;
import net.lelyak.edu.rest.service.impl.CommentServiceImpl;
import net.lelyak.edu.rest.service.impl.PostServiceImpl;
import net.lelyak.edu.rest.service.impl.UserServiceImpl;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;

/**
 * @author Nazar Lelyak.
 */
@Component
@AllArgsConstructor
public class DefaultRestInitializer /*implements ApplicationRunner*/ {

    private final UserServiceImpl userServiceImpl;
    private final PostServiceImpl postServiceImpl;
    private final CommentServiceImpl commentServiceImpl;


//    @Override
    public void run(ApplicationArguments args) throws Exception {
        BlogUser carlos = BlogUser.builder()
                .userName("carlos")
                .password("secret")
                .email("carlos@gmail.com")
                .role(Role.USER)
                .enabled(true)
                .build();

        userServiceImpl.createUser(carlos);

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

        postServiceImpl.addPost(firstPost);
        postServiceImpl.addPost(secondPost);
        postServiceImpl.addPost(thirdPost);

        BlogUser sailor = BlogUser.builder()
                .userName("sailor").password("123").email("sailor@gmail.com").role(Role.USER).enabled(true).build();
        userServiceImpl.createUser(sailor);

        Comment commentToFirstPost = Comment.builder().commentText("you are an idiot!")
                .user(sailor).post(firstPost).build();
        Comment secondCommentToFirstPost = Comment.builder().commentText("You should sail to Antarctica!")
                .user(sailor).post(firstPost).build();
        Comment commentToSecondPost = Comment.builder().commentText("Shut up!")
                .user(sailor).post(secondPost).build();
        Comment commentToThirdPost = Comment.builder().commentText("You need to go and eat spinach! As much as you can!")
                .user(sailor).post(thirdPost).build();

        commentServiceImpl.addComment(commentToFirstPost);
        commentServiceImpl.addComment(secondCommentToFirstPost);
        commentServiceImpl.addComment(commentToSecondPost);
        commentServiceImpl.addComment(commentToThirdPost);
    }
}
