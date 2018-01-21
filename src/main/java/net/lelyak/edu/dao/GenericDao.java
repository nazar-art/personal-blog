package net.lelyak.edu.dao;

import java.util.List;

public interface GenericDao<ENTITY, ID> {

    List<ENTITY> getAll();

    Long create(ENTITY entity);

    ENTITY read(ID id);

    void update(ENTITY entity);

    void delete(ENTITY entity);
}
