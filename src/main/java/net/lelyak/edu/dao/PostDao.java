package net.lelyak.edu.dao;

import net.lelyak.edu.model.Post;

import java.util.List;

/**
 * @author Nazar Lelyak.
 */
public interface PostDao extends GenericDao<Post, Long> {

    List<Post> findPostsByUserName(String userName);
}
