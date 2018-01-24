package net.lelyak.edu.rest.service.impl;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import net.lelyak.edu.model.Comment;
import net.lelyak.edu.rest.repository.CommentRepository;
import net.lelyak.edu.rest.service.CommentService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Nazar Lelyak.
 */
@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    @Override
    public List<Comment> findAllCommentsByPostId(Long id) {
        List<Comment> result = Lists.newArrayList();
        result.addAll(commentRepository.findByPost_Id(id));
        return result;
    }

    @Override
    public Comment findComment(Long id) {
        return commentRepository.findOne(id);
    }

    @Override
    public void addComment(Comment comment) {
        comment.setCreatedDate(LocalDateTime.now());
        commentRepository.save(comment);
    }

    @Override
    public void updateComment(Long id, Comment comment) {
        commentRepository.save(comment);
    }

    @Override
    public void deleteComment(Long id) {
        commentRepository.delete(id);
    }

    @Override
    public void deleteAllCommentsByPostId(Long id) {
        findAllCommentsByPostId(id)
                .forEach(post -> deleteComment(post.getId()));
    }
}
