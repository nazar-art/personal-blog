package net.lelyak.edu.dao.impl;

import lombok.extern.slf4j.Slf4j;
import net.lelyak.edu.dao.CommentDao;
import net.lelyak.edu.model.Comment;
import net.lelyak.edu.model.Post;
import org.hibernate.Criteria;
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
    public List<Comment> findCommentsByPostId(Long id) {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(Post.class);
        // todo
        return null;
    }
}
