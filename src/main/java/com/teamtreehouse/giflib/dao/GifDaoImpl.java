package com.teamtreehouse.giflib.dao;

import com.teamtreehouse.giflib.model.Gif;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class GifDaoImpl implements GifDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Gif> findAll() {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Gif> criteria = criteriaBuilder.createQuery(Gif.class);
            criteria.from(Gif.class);
            return session.createQuery(criteria).getResultList();
        }
    }

    @Override
    public Gif findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Gif.class, id);
        }
    }

    @Override
    public void save(Gif gif) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.saveOrUpdate(gif);
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(Gif gif) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(gif);
            session.getTransaction().commit();
        }
    }
}
