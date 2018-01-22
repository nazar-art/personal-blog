package net.lelyak.edu.rest.service.impl;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.lelyak.edu.model.Post;
import net.lelyak.edu.utils.exception.BadRequestException;
import net.lelyak.edu.utils.exception.NotPresentedInDbException;
import net.lelyak.edu.rest.repository.PostRepository;
import net.lelyak.edu.rest.repository.UserRepository;
import net.lelyak.edu.rest.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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
        result.addAll(postRepository.findByUser_UserName(userName));
        return result;
    }

    @Override
    public Page<Post> listAllPostsByPage(String userName, Pageable pageable) {
        Page<Post> postsPage = postRepository.findByUser_UserName(userName, pageable);
        log.info("Posts page object: {} with pages: {} and total elements: {}",
                postsPage, postsPage.getTotalPages(), postsPage.getTotalElements());
        return postsPage;
    }

    @Override
    public Post findPost(String userName, Long id) {
        validateDbPresence(userName);

//        Post result = postRepository.findOne(id);
//        validateRestUrlParameters(userName, result);
//        return result;

        return postRepository.findOne(id);
    }

    @Override
    public void addPost(Post post) {
        post.setCreatedDate(LocalDateTime.now());
        postRepository.save(post);
    }

    @Override
    public void updatePost(String userName, Long id, Post post) {
        validateDbPresence(userName);
        validateRestUrlParameters(userName, findPost(userName, id));

        postRepository.save(post);
    }

    @Override
    public void deletePost(String userName, Long id) {
        validateDbPresence(userName);
//        validateRestUrlParameters(userName, findPost(userName, id));
        postRepository.delete(id);
    }

    @Override
    public void deleteAllPostsByUserName(String userName) {
        findAllPostsByUserName(userName)
                .forEach(post -> deletePost(userName, post.getId()));
    }


    private void validateRestUrlParameters(String userName, Post result) {
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
}
