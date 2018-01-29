package net.lelyak.edu.web;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import net.lelyak.edu.model.BlogUser;
import net.lelyak.edu.model.Post;
import net.lelyak.edu.rest.service.PostService;
import net.lelyak.edu.rest.service.UserService;
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
public class PostControllerSystemTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Autowired
    private UserService userService;
    @Autowired
    private PostService postService;


    private BlogUser magelan = BlogUser.builder()
            .userName("magelan")
            .password("magelan")
            .email("magelan@gmail.com")
            .build();

    private List<Post> posts = Lists.newArrayList(
            Post.builder()
                    .postText("First post")
                    .user(magelan)
                    .build(),
            Post.builder()
                    .postText("Second post")
                    .user(magelan)
                    .build());

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
//        this.mockMvc.perform(get("/posts")
//                .header(HttpHeaders.AUTHORIZATION, "Basic " +
//                        Base64Utils.encodeToString(String.join(":", magelan.getUserName(), magelan.getPassword()).getBytes()))
        //
//        this.mockMvc.perform(formLogin("/login").user("username", magelan.getUserName()).password("password", magelan.getPassword())
//                .acceptMediaType(MediaType.parseMediaType("text/html;charset=UTF-8")))

        this.mockMvc.perform(get("/posts").with(httpBasic("magelan", "magelan"))
                .accept(MediaType.parseMediaType("text/html;charset=UTF-8")))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(content().string(allOf(
                        containsString("First post"),
                        containsString("Second post")
                )));
    }
}
