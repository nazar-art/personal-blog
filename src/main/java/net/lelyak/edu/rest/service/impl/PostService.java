package net.lelyak.edu.rest.service.impl;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import net.lelyak.edu.model.Post;
import net.lelyak.edu.utils.exception.BadRequestException;
import net.lelyak.edu.utils.exception.NotPresentedInDbException;
import net.lelyak.edu.rest.repository.PostRepository;
import net.lelyak.edu.rest.repository.UserRepository;
import net.lelyak.edu.rest.service.IPostService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Nazar Lelyak.
 */
@Service
@AllArgsConstructor
public class PostService implements IPostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Override
    public List<Post> findAllPostsByUserName(String userName) {
        List<Post> result = Lists.newArrayList();
        result.addAll(postRepository.findByUser_UserName(userName));
        return result;
    }

    @Override
    public Post findPost(String userName, Long id) {
        Post result;
        if (userRepository.exists(userName)) {
            result = postRepository.findOne(id);

            boolean postBelongsToUser = result.getUser().getUserName().equals(userName);
            if (!postBelongsToUser) {
                throw new BadRequestException(userName);
            }

        } else {
            throw new NotPresentedInDbException(userName);
        }

        return result;
    }

    @Override
    public void addPost(Post post) {
        post.setCreatedDate(LocalDateTime.now());
        postRepository.save(post);
    }

    @Override
    public void updatePost(String userName, Long id, Post post) {
        // add validation for posts and comments
        postRepository.save(post);
    }

    @Override
    public void deletePost(String userName, Long id) {
        postRepository.delete(id);
    }

    @Override
    public void deleteAllPostsByUserName(String userName) {
        findAllPostsByUserName(userName)
                .forEach(post -> deletePost(userName, post.getId()));
    }
}
