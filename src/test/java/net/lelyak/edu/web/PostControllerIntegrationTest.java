package net.lelyak.edu.web;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.google.common.collect.Lists;
import net.lelyak.edu.controller.PostController;
import net.lelyak.edu.model.BlogUser;
import net.lelyak.edu.model.Post;
import net.lelyak.edu.rest.service.impl.CommentServiceImpl;
import net.lelyak.edu.rest.service.impl.PostServiceImpl;
import net.lelyak.edu.rest.service.impl.UserDetailsServiceImpl;
import net.lelyak.edu.rest.service.impl.UserServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.htmlunit.MockMvcWebClientBuilder;

import java.util.List;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Nazar Lelyak.
 */
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@WebMvcTest(PostController.class)
public class PostControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    private WebClient webClient;

    @MockBean
    private PostServiceImpl postService;
    @MockBean
    private UserServiceImpl userService;
    @MockBean
    private CommentServiceImpl commentService;


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

    @SuppressWarnings("unchecked")
    private Page<Post> pagePosts = new PageImpl(posts);

    @Before
    public void setUp() throws Exception {
        Mockito.when(postService.listAllPostsByPage(new PageRequest(1, 5))).thenReturn(pagePosts);

        webClient = MockMvcWebClientBuilder.mockMvcSetup(mockMvc)
                .useMockMvcForHosts("posts.com", "myblog.org")
                .build();
    }

    @Test
    public void requestIsSuccessfullyProcessedWithAvailablePosts() throws Exception {
        this.mockMvc.perform(get("/posts").with(user("user"))
                .accept(MediaType.parseMediaType("text/html;charset=UTF-8")))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(content().string(allOf(
                        containsString("First post"),
                        containsString("Second post")))
                );
    }

    @Test
    public void postsPageContentIsRenderedAsHtmlWithListOfPosts() throws Exception {
        HtmlPage page = webClient.getPage("http://posts.com/posts");
//        page.getElementById()
    }
}
