package net.lelyak.edu.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.lelyak.edu.model.BlogUser;
import net.lelyak.edu.model.Post;
import net.lelyak.edu.rest.service.CommentService;
import net.lelyak.edu.rest.service.PostService;
import net.lelyak.edu.rest.service.UserService;
import net.lelyak.edu.web.PageWrapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Slf4j
@Controller
@AllArgsConstructor
public class PostController {

    private final PostService postService;
    private final UserService userService;
    private final CommentService commentService;

    /**
     * List all user posts.
     */
    @GetMapping(value = {"/posts", "/posts.html"})
    public String userHomePage(Model model, Pageable pageable) {

        Page<Post> postsPage = postService.listAllPostsByPage(pageable);
        PageWrapper<Post> page = new PageWrapper<>(postsPage, "/posts");

        model.addAttribute("posts", page.getContent());
        model.addAttribute("page", page);

        return "post/posts";
    }

    /**
     * View post details.
     */
    @GetMapping("post/{postId}")
    public String viewPost(@PathVariable("postId") Long id, Model model) {
        model.addAttribute("post", postService.findPost(id));
        model.addAttribute("comments", commentService.findAllCommentsByPostId(id));
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
    public String savePost(Post post, Principal principal) {

        String currentUserName = principal.getName();
        BlogUser currentUser = userService.getUser(currentUserName);

        post.setUser(currentUser);
        postService.createPost(post);
        return "redirect:post/" + post.getId();
    }

    /**
     * Edit created post
     */
    @GetMapping("/post/edit/{postId}")
    public String editPost(@PathVariable("postId") Long id, Model model) {
        Post postToEdit = postService.findPost(id);
        model.addAttribute("post", postToEdit);
        return "post/addPost";
    }

    /**
     * Delete created post.
     */
    @GetMapping("post/delete/{postId}")
    public String deletePost(@PathVariable("postId") Long id) {
        log.debug("Delete post id value: {}", id);
        postService.deletePost(id);
        return "redirect:/posts";
    }
}
