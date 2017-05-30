package com.teamtreehouse.giflib.dao;

import com.teamtreehouse.giflib.model.Category;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

@Repository // Always implement the implementation, not the interface
public class CategoryDaoImpl implements CategoryDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Category> findAll() {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Category> criteria = builder.createQuery(Category.class);
            criteria.from(Category.class);
            return session.createQuery(criteria).getResultList();
        }
    }

    @Override
    public Category findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Category.class, id);
        }
    }

    @Override
    public void save(Category category) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(category);
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(Category category) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(category);
            session.getTransaction().commit();
        }
    }
}
