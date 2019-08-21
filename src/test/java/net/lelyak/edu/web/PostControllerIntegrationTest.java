package net.lelyak.edu.web;

import lombok.extern.slf4j.Slf4j;
import net.lelyak.edu.model.BlogUser;
import net.lelyak.edu.model.Post;
import net.lelyak.edu.rest.service.PostService;
import net.lelyak.edu.rest.service.UserService;
import net.lelyak.edu.utils.generator.TestDataGenerator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Nazar Lelyak.
 */
@Slf4j
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class PostControllerIntegrationTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Autowired
    private UserService userService;
    @Autowired
    private PostService postService;


    private BlogUser magelan = TestDataGenerator.buildMagelanUser();
    private List<Post> posts = TestDataGenerator.buildPostsList(magelan, "First post", "Second post");

    @Before
    public void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

        userService.createUser(magelan);
        posts.forEach(p -> postService.createPost(p));
    }

    @After
    public void tearDown() {
        userService.deleteUser(magelan.getUserName());
    }

    @Test
    public void allPostsFromDatabaseAreAvailable() throws Exception {
        this.mockMvc.perform(get("/posts")
                .with(httpBasic(magelan.getUserName(), magelan.getPassword()))
                .accept(MediaType.parseMediaType("text/html;charset=UTF-8")))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(content().string(allOf(
                        containsString("First post"),
                        containsString("Second post")
                )));
    }

    @Test
    public void viewSeparatePostPageIsAvailable() throws Exception {
        List<Post> postsByUserName = postService.findAllPostsByUserName(magelan.getUserName());
        Post firstPost = postsByUserName.get(0);

        this.mockMvc.perform(get("/post/" + firstPost.getId())
                .with(httpBasic(magelan.getUserName(), magelan.getPassword()))
                .accept(MediaType.parseMediaType("text/html;charset=UTF-8")))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(content().string(allOf(
                        containsString(firstPost.getPostText())
                )));
    }

    @Test
    public void throwExceptionIfPostIdIsIncorrect() throws Exception {
        int test_id = 99999;
        this.mockMvc.perform(get("/post/" + test_id)
                .with(httpBasic(magelan.getUserName(), magelan.getPassword()))
                .accept(MediaType.parseMediaType("text/html;charset=UTF-8")))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void postsPaginationOfPostsPerPageShowOnlyOnePost() throws Exception {
        this.mockMvc.perform(get("/posts?page=0&size=1")
                .with(httpBasic(magelan.getUserName(), magelan.getPassword()))
                .accept(MediaType.parseMediaType("text/html;charset=UTF-8")))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(content().string(allOf(
                        containsString("First post")
                )))
                .andExpect(content().string(allOf(
                        not(containsString("Second post"))
                )));

    }

    @Test
    public void postsPaginationOfPostsPerPageShowZeroPosts() throws Exception {
        this.mockMvc.perform(get("/posts?page=5&size=5")
                .with(httpBasic(magelan.getUserName(), magelan.getPassword()))
                .accept(MediaType.parseMediaType("text/html;charset=UTF-8")))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(content().string(allOf(
                        not(containsString("First post")),
                        not(containsString("Second post"))
                )));
    }

    @Test
    public void userCanLogoutAndSeeLoginPage() throws Exception {
        this.mockMvc.perform(get("/login?logout")
                .with(httpBasic(magelan.getUserName(), magelan.getPassword()))
                .accept(MediaType.parseMediaType("text/html;charset=UTF-8")))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(content().string(allOf(
                        containsString("Please Sign In"),
                        containsString("You have been logged out.")
                )));
    }
}
