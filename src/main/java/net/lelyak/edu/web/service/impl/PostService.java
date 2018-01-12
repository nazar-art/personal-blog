package net.lelyak.edu.web.service.impl;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import net.lelyak.edu.model.Post;
import net.lelyak.edu.web.repository.PostRepository;
import net.lelyak.edu.web.service.IPostService;
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

    @Override
    public List<Post> findAllPostsByUserName(String userName) {
        List<Post> result = Lists.newArrayList();
        result.addAll(postRepository.findByUser_UserName(userName));
        return result;
    }

    @Override
    public Post findPost(Long id) {
        return postRepository.findOne(id);
    }

    @Override
    public void addPost(Post post) {
        post.setCreatedDate(LocalDateTime.now());
        postRepository.save(post);
    }

    @Override
    public void updatePost(Long id, Post post) {
        postRepository.save(post);
    }

    @Override
    public void deletePost(Long id) {
        postRepository.delete(id);
    }

    @Override
    public void deleteAllPostsByUserName(String userName) {
        findAllPostsByUserName(userName)
                .forEach(post -> deletePost(post.getId()));
    }
}
