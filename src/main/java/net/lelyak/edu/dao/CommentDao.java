package net.lelyak.edu.dao;

import net.lelyak.edu.model.Comment;

import java.util.List;

/**
 * @author Nazar Lelyak.
 */
public interface CommentDao extends GenericDao<Comment, Long> {

    List<Comment> findCommentsByPostId(Long id);
}
