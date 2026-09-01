package vn.iotstar.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import vn.iotstar.config.JpaConfig;
import vn.iotstar.entity.User;

public class UserDao implements IUserDao {

    @Override
    public void insert(User user) {
        EntityManager enma = JpaConfig.getEntityManager();
        EntityTransaction trans = enma.getTransaction();
        try {
            trans.begin();
            enma.persist(user);
            trans.commit();
        } catch (Exception e) {
            e.printStackTrace();
            trans.rollback();
            throw e;
        } finally {
            enma.close();
        }
    }

    @Override
    public void update(User user) {
        EntityManager enma = JpaConfig.getEntityManager();
        EntityTransaction trans = enma.getTransaction();
        try {
            trans.begin();
            enma.merge(user);
            trans.commit();
        } catch (Exception e) {
            e.printStackTrace();
            trans.rollback();
            throw e;
        } finally {
            enma.close();
        }
    }

    @Override
    public User findById(int id) {
        EntityManager enma = JpaConfig.getEntityManager();
        try {
            return enma.find(User.class, id);
        } finally {
            enma.close();
        }
    }

    @Override
    public User findByEmail(String email) {
        EntityManager enma = JpaConfig.getEntityManager();
        try {
            String jpql = "SELECT u FROM User u WHERE u.email = :email";
            TypedQuery<User> query = enma.createQuery(jpql, User.class);
            query.setParameter("email", email);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            enma.close();
        }
    }
}
