package net.lelyak.edu.dao.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.lelyak.edu.dao.PostDao;
import net.lelyak.edu.model.Post;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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

    public static void main(String[] args) {
        PostDaoImpl postDao = new PostDaoImpl();
        List<Post> posts = postDao.findPostsByUserName("carlos");
        System.out.println(posts);
    }
}
