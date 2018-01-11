package net.lelyak.edu.web.service;

import net.lelyak.edu.model.Comment;

import java.util.List;

/**
 * @author Nazar Lelyak.
 */
public interface ICommentService {
    
    List<Comment> findAllCommentsByPostId(Long id);

    Comment findComment(Long id);

    void addComment(Comment post);

    void updateComment(Long id, Comment post);

    void deleteComment(Long id);

    void deleteAllCommentsByPostId(Long id);
}