package net.lelyak.edu.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.lelyak.edu.model.BlogUser;
import net.lelyak.edu.model.Post;
import net.lelyak.edu.rest.service.impl.CommentServiceImpl;
import net.lelyak.edu.rest.service.impl.PostServiceImpl;
import net.lelyak.edu.rest.service.impl.UserServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
@Slf4j
public class PostController {

    private final PostServiceImpl postServiceImpl;
    private final UserServiceImpl userServiceImpl;
    private final CommentServiceImpl commentServiceImpl;

    @GetMapping("/")
    public String mainUrl() {
        return "redirect:/posts";
    }

    /**
     * List all user posts.
     */
    @GetMapping("/posts")
    public String userHomePage(Model model, Pageable pageable) {
        String currentUserName = getCurrentUserName();

        Page<Post> postsPage = postServiceImpl.listAllPostsByPage(currentUserName, pageable);
        PageWrapper<Post> page = new PageWrapper<>(postsPage, "/posts");

        model.addAttribute("posts", page.getContent());
        model.addAttribute("page", page);

        log.debug("Returning posts:");
        return "/post/posts";
    }

    /**
     * View post details.
     */
    @GetMapping("post/{postId}")
    public String viewPost(@PathVariable("postId") Long id, Model model) {
        model.addAttribute("post", postServiceImpl.findPost(getCurrentUserName(), id));
        model.addAttribute("comments", commentServiceImpl.findAllCommentsByPostId(id));
        return "post/viewPost";
    }

    /**
     * Open create new post page.
     */
    @GetMapping("/post/new")
    public String createPost(Model model) {
        model.addAttribute("post", Post.builder().build());
        return "post/addPost";
    }

    /**
     * Save new post to DB.
     */
    @PostMapping(value = "post")
    public String savePost(Post post) {
        String currentUserName = getCurrentUserName();
        BlogUser currentUser = userServiceImpl.getUser(currentUserName);

        post.setUser(currentUser);
        postServiceImpl.addPost(post);
        return "redirect:/post/" + post.getId();
    }

    /**
     * Edit created post
     */
    @GetMapping("/post/edit/{postId}")
    public String editPost(@PathVariable("postId") Long id, Model model) {
        Post postToEdit = postServiceImpl.findPost(getCurrentUserName(), id);
        model.addAttribute("post", postToEdit);
        return "post/addPost";
    }

    /**
     * Delete created post.
     */
    @GetMapping("post/delete/{postId}")
    public String deletePost(@PathVariable("postId") Long id) {
        postServiceImpl.deletePost(getCurrentUserName(), id);
        return "redirect:/posts";
    }


    private String getCurrentUserName() {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        String userName = authentication.getName();
        log.debug("Defining current user name: {}", userName);
        return userName;
    }
}
