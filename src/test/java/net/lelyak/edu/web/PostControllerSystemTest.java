package net.lelyak.edu.web;

import lombok.extern.slf4j.Slf4j;
import net.lelyak.edu.model.BlogUser;
import net.lelyak.edu.model.Post;
import net.lelyak.edu.rest.service.PostService;
import net.lelyak.edu.rest.service.UserService;
import net.lelyak.edu.utils.exception.BadRequestException;
import net.lelyak.edu.utils.generator.TestDataGenerator;
import org.apache.commons.lang3.RandomUtils;
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

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.not;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

/**
 * @author Nazar Lelyak.
 */
@Slf4j
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class PostControllerSystemTest {

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
        log.info("Init started !!!!");
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

        userService.createUser(magelan);
        posts.forEach(p -> postService.createPost(p));

        log.info("Init ENDED !!!!");
    }

    @After
    public void tearDown() throws Exception {
        log.info("tearDown() started");

        userService.deleteUser(magelan.getUserName());

        log.info("tearDown() ended");
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
    public void viewSeparatePostPagePerEachDbPostIsAvailable() throws Exception {
        List<Post> postsByUserName = postService.findAllPostsByUserName(magelan.getUserName());

        postsByUserName.forEach(post -> {
            log.info("POST_DETAILS: {} FOR USER: {}", post, magelan);

            try {
                this.mockMvc.perform(get("/post/" + post.getId())
                        .with(httpBasic(magelan.getUserName(), magelan.getPassword()))
                        .accept(MediaType.parseMediaType("text/html;charset=UTF-8")))
                        .andExpect(status().isOk())
                        .andExpect(content().contentType("text/html;charset=UTF-8"))
                        .andExpect(content().string(allOf(
                                containsString(post.getPostText())
                        )));
            } catch (Exception e) {
                log.error("Exception happen during viewing the post page: {}", e.getCause());
            }
        });
    }

    @Test
    public void ifPostIdIsIncorrectThrowException() throws Exception {
        int id = RandomUtils.nextInt(10000, 20000);
        this.mockMvc.perform(get("/post/" + id)
                .with(httpBasic(magelan.getUserName(), magelan.getPassword()))
                .accept(MediaType.parseMediaType("text/html;charset=UTF-8")))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void postsPaginationControlsNumberOfPostsPerPageAndCanShowOnlyOnePost() throws Exception {
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
    public void postsPaginationControlsNumberOfPostsPerPageAndCanShowZeroPosts() throws Exception {
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
