package com.teamtreehouse.giflib.dao;

import com.teamtreehouse.giflib.model.Category;
import com.teamtreehouse.giflib.model.Gif;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
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
            Category category = session.get(Category.class, id);
            // Initialize the gifs otherwise you will get a LazyInitializationError
            // since you'll be trying to access gifs outside of a hibernate session
            // We are initializing the gifs collection only WHEN WE NEED IT
            Hibernate.initialize(category.getGifs());
            return category;
        }
    }

    @Override
    public void save(Category category) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            // Hibernate will be smart enough to determine if the entity has already
            // been persisted. If it has it will update rather than persist a new entry
            session.saveOrUpdate(category);
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
