package net.lelyak.edu.dao.impl;

import lombok.extern.slf4j.Slf4j;
import net.lelyak.edu.dao.UserDao;
import net.lelyak.edu.model.BlogUser;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * @author Nazar Lelyak.
 */
@Slf4j
@Repository
public class UserDaoImpl extends AbstractGenericDao<BlogUser, String> implements UserDao {

    public UserDaoImpl() {
        super(BlogUser.class);
    }

    @Override
    public BlogUser findByUserName(String name) {
        BlogUser user = (BlogUser) getSessionFactory().getCurrentSession().createCriteria(BlogUser.class)
        .add(Restrictions.eq("userName", name))
        .list().get(0);

        log.info("Getting user from DB: {} by name: {}", user, name);
        return user;
    }

    @Override
    public BlogUser findByEmail(String email) {
        BlogUser user = (BlogUser) getSessionFactory().getCurrentSession().createCriteria(BlogUser.class)
        .add(Restrictions.eq("email", email))
        .list().get(0);

        log.info("Getting user from DB: {} by email: {}", user, email);
        return user;
    }
}
