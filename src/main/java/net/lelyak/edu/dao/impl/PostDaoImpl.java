package net.lelyak.edu.dao.impl;

import lombok.extern.slf4j.Slf4j;
import net.lelyak.edu.dao.PostDao;
import net.lelyak.edu.model.Post;
import org.hibernate.Query;
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
        String hql = "SELECT post FROM posts WHERE user_user_name = :userName";

        Query query = getSessionFactory().getCurrentSession().createQuery(hql);
        query.setParameter("userName", userName);

        @SuppressWarnings("unchecked")
        List<Post> posts = (List<Post>) query.list();
        log.debug("Getting list of posts: {} by user name: {}", posts, userName);
        return posts;
    }
}
