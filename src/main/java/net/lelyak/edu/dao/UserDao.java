package net.lelyak.edu.dao;

import net.lelyak.edu.model.BlogUser;

/**
 * @author Nazar Lelyak.
 */
public interface UserDao extends GenericDao<BlogUser, String> {
    BlogUser findByUserName(String name);

    BlogUser findByEmail(String email);
}
