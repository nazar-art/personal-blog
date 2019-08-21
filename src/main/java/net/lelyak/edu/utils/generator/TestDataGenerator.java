package net.lelyak.edu.utils.generator;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import net.lelyak.edu.model.BlogUser;
import net.lelyak.edu.model.Comment;
import net.lelyak.edu.model.Post;
import net.lelyak.edu.model.Role;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * @author Nazar Lelyak.
 */
@Slf4j
public class TestDataGenerator {

    public static BlogUser buildMagelanUser() {
        BlogUser magelan = BlogUser.builder()
                .userName("magelan")
                .password("magelan")
                .email("magelan@gmail.com")
                .role(Role.USER)
                .enabled(true)
                .build();

        log.debug("Build 'magelan' test user: {}", magelan);
        return magelan;
    }

    public static Post buildPost(String postText, BlogUser user) {
        Post post = Post.builder()
                .postText(postText)
                .user(user)
                .createdDate(LocalDateTime.now())
                .build();
        log.debug("Build post for test: {}", post);
        return post;
    }

    public static Comment buildComment(BlogUser user, Post post, String text) {
        return Comment.builder()
                .post(post)
                .user(user)
                .commentText(text)
                .createdDate(LocalDateTime.now())
                .build();
    }

    public static List<Comment> addCommentToPostList(List<Post> posts, BlogUser user, String text) {
        ArrayList<Comment> result = Lists.newArrayList();
        posts.forEach(p -> result.add(buildComment(user, p, text)));
        return result;
    }


    public static List<Post> buildPostsList(BlogUser user, String... postsMessages) {
        List<Post> list = Stream.of(postsMessages)
                .map(text -> buildPost(text, user))
                .collect(toList());
        log.debug("Build list of posts for test: {}", list);
        return list;
    }

}
