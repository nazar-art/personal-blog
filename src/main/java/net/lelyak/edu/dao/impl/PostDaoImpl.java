package net.lelyak.edu.dao.impl;

import lombok.extern.slf4j.Slf4j;
import net.lelyak.edu.dao.PostDao;
import net.lelyak.edu.model.Post;
import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Nazar Lelyak.
 */
@Slf4j
@Repository
public class PostDaoImpl extends AbstractGenericDao<Post, Long> implements PostDao {

    public PostDaoImpl() {
        super(Post.class);
    }

    @Override
    public List<Post> findPostsByUserName(String userName) {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(Post.class);
        // todo
        return null;
    }
}
