package net.lelyak.edu.web;

import net.lelyak.edu.controller.PageWrapper;
import net.lelyak.edu.controller.PostController;
import net.lelyak.edu.model.BlogUser;
import net.lelyak.edu.model.Comment;
import net.lelyak.edu.model.Post;
import net.lelyak.edu.rest.service.CommentService;
import net.lelyak.edu.rest.service.PostService;
import net.lelyak.edu.rest.service.UserService;
import net.lelyak.edu.utils.generator.TestDataGenerator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.ExtendedModelMap;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.collection.IsMapContaining.hasEntry;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Nazar Lelyak.
 */
@RunWith(MockitoJUnitRunner.class)
public class PostControllerTest {

    public static final long TEST_POST_ID = 1L;

    @Mock
    private PostService postService;
    @Mock
    private UserService userService;
    @Mock
    private CommentService commentService;

    private PostController postController;

    private MockMvc mockMvc;

//    @Autowired
//    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private BlogUser magelan = TestDataGenerator.buildMagelanUser();
    private List<Post> posts = TestDataGenerator.buildPostsList(magelan, "First post", "Second post");
    private List<Comment> comments = TestDataGenerator.addCommentToPostList(posts, magelan, "It is a comment");
    private Post firstPost = posts.get(0);

    private Pageable pageable = new PageRequest(0, 2);
    @SuppressWarnings("unchecked")
    private Page<Post> pagePosts = new PageImpl(posts);
    private PageWrapper<Post> page = new PageWrapper<>(pagePosts, "/posts");

    @Before
    public void setUp() throws Exception {
        postController = new PostController(postService, userService, commentService);
        mockMvc = MockMvcBuilders.standaloneSetup(postController)
//                .setCustomArgumentResolvers(pageableArgumentResolver)
                .build();

        // mocks configuration
//        when(postService.listAllPostsByPage(pageable)).thenReturn(pagePosts);
//        when(postService.findPost(TEST_POST_ID)).thenReturn(firstPost);
//        when(commentService.findAllCommentsByPostId(TEST_POST_ID)).thenReturn(comments);
    }

    @Test
    public void allPostsAreAddedToModelForPostsView() throws Exception {
        when(postService.listAllPostsByPage(pageable)).thenReturn(pagePosts);

        ExtendedModelMap model = new ExtendedModelMap();

        assertThat(postController.userHomePage(model, pageable), equalTo("post/posts"));
        assertThat(model.asMap(), hasEntry("posts", page.getContent()));
    }

//    @Test // fails with NPE
    public void requestForPostsIsSuccessfullyProcessedWithAvailablePosts() throws Exception {
        this.mockMvc.perform(get("/posts")
                .with(httpBasic(magelan.getUserName(), magelan.getPassword())))
                .andExpect(status().isOk())
                .andExpect(model().attribute("posts", equalTo(page.getContent())))
                .andExpect(forwardedUrl("post/posts"));
    }

    @Test
    public void allCommentsAreAddedToPostForPostView() throws Exception {
        when(postService.findPost(TEST_POST_ID)).thenReturn(firstPost);
        when(commentService.findAllCommentsByPostId(TEST_POST_ID)).thenReturn(comments);

        ExtendedModelMap model = new ExtendedModelMap();

        assertThat(postController.viewPost(TEST_POST_ID, model), equalTo("post/viewPost"));
        assertThat(model.asMap(), hasEntry("post", firstPost));
        assertThat(model.asMap(), hasEntry("comments", comments));
    }
}
