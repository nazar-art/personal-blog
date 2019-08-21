package net.lelyak.edu.dao.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.lelyak.edu.dao.GenericDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;

@Component
@AllArgsConstructor
public abstract class AbstractGenericDao<ENTITY, ID extends Serializable> implements GenericDao<ENTITY, ID> {

    @Getter
    private SessionFactory sessionFactory;
    private Class<ENTITY> entityClass;

    protected AbstractGenericDao() {
    }

    protected AbstractGenericDao(Class<ENTITY> entityClass) {
        super();
        this.entityClass = entityClass;
    }

    @Override
    public List<ENTITY> getAll() {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        transaction.commit();
        return null;
    }

    @Override
    public Long create(ENTITY entity) {
        Session session = sessionFactory.getCurrentSession();
        return (Long) session.save(entity);
    }

    @Override
    public ENTITY read(ID id) {
        Session session = sessionFactory.getCurrentSession();
        @SuppressWarnings("unchecked")
        ENTITY fetchedEntity = session.get(entityClass, id);
        return fetchedEntity;
    }

    @Override
    public void update(ENTITY entity) {
        Session session = sessionFactory.getCurrentSession();
        session.update(entity);
    }

    @Override
    public void delete(ENTITY entity) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.delete(entity);
        transaction.commit();
    }
}