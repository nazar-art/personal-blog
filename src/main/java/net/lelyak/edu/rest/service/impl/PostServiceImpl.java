package net.lelyak.edu.rest.service.impl;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.lelyak.edu.model.BlogUser;
import net.lelyak.edu.model.Post;
import net.lelyak.edu.utils.exception.BadRequestException;
import net.lelyak.edu.utils.exception.NotPresentedInDbException;
import net.lelyak.edu.rest.repository.PostRepository;
import net.lelyak.edu.rest.repository.UserRepository;
import net.lelyak.edu.rest.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @author Nazar Lelyak.
 */
@Service
@AllArgsConstructor
@Slf4j
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Override
    public List<Post> findAllPostsByUserName(String userName) {
        List<Post> result = Lists.newArrayList();
        Optional<BlogUser> user = userRepository.findByUserName(userName);
        result.addAll(postRepository.findByUser(user.get()));
        return result;
    }

    @Override
    public Page<Post> listAllPostsByPage(Pageable pageable) {
        String userName = getCurrentUserName();
        Page<Post> postsPage = postRepository.findByUser_UserName(userName, pageable);

        log.info("Posts page object: {} with pages: {} and total elements: {}",
                postsPage, postsPage.getTotalPages(), postsPage.getTotalElements());

        return postsPage;
    }

    @Override
    public Post findPost(Long id) {
        String userName = getCurrentUserName();
        validateDbPresence(userName);

        Post result = postRepository.findOne(id);
        validatePostRelevance(userName, result);

        return result;
    }

    @Override
    public void createPost(Post post) {
        log.debug("CREATE_POST: {}", post);

        post.setCreatedDate(LocalDateTime.now());
        postRepository.save(post);

        log.debug("CREATED_NEW_POST: {}", post);
    }

    @Override
    public void updatePost(Long id, Post post) {
        String userName = getCurrentUserName();

        validateDbPresence(userName);
        validatePostRelevance(userName, findPost(id));

        postRepository.save(post);
    }

    @Override
    public void deletePost(Long id) {
        String userName = getCurrentUserName();

        validateDbPresence(userName);
        validatePostRelevance(userName, findPost(id));

        postRepository.delete(id);
    }

    @Override
    public void deleteAllPostsByUserName(String userName) {
        findAllPostsByUserName(userName)
                .forEach(post -> deletePost(post.getId()));
    }


    private void validatePostRelevance(String userName, Post result) {
        boolean postBelongsToUser = result.getUser().getUserName().equals(userName);
        if (!postBelongsToUser) {
            throw new BadRequestException(userName);
        }
    }

    private void validateDbPresence(String userName) {
        if (!userRepository.exists(userName)) {
            throw new NotPresentedInDbException(userName);
        }
    }

    private String getCurrentUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        log.debug("Current USER_NAME: {}", userName);
        return userName;
    }
}
