package net.lelyak.edu.dao.impl;

import lombok.extern.slf4j.Slf4j;
import net.lelyak.edu.dao.CommentDao;
import net.lelyak.edu.model.Comment;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Nazar Lelyak.
 */
@Slf4j
@Repository
public class CommentDaoImpl extends AbstractGenericDao<Comment, Long> implements CommentDao {

    public CommentDaoImpl() {
        super(Comment.class);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Comment> findCommentsByPostId(Long id) {
        List<Comment> commentsList = getSessionFactory().getCurrentSession()
                .createCriteria(Comment.class)
                .add(Restrictions.eq("post_id", id))
                .list();
        log.debug("ALL COMMENTS from DB: {} for POST_ID: {}", commentsList, id);
        return commentsList;
    }
}
