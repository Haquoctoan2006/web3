package vn.iotstar.dao;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import vn.iotstar.config.JpaConfig;
import vn.iotstar.entity.Order;

public class OrderDao implements IOrderDao {

    @Override
    public void insert(Order order) {
        EntityManager enma = JpaConfig.getEntityManager();
        EntityTransaction trans = enma.getTransaction();
        try {
            trans.begin();
            enma.persist(order);
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
    public Order findById(int id) {
        EntityManager enma = JpaConfig.getEntityManager();
        try {
            return enma.find(Order.class, id);
        } finally {
            enma.close();
        }
    }

    @Override
    public List<Order> findByUser(int userId) {
        EntityManager enma = JpaConfig.getEntityManager();
        try {
            // JOIN FETCH de load san orderDetails (va product ben trong) ngay trong
            // luc con session, tranh LazyInitializationException khi JSP render sau khi
            // EntityManager da dong.
            String jpql = "SELECT DISTINCT o FROM Order o "
                    + "LEFT JOIN FETCH o.orderDetails od "
                    + "LEFT JOIN FETCH od.product "
                    + "WHERE o.user.userId = :userId "
                    + "ORDER BY o.orderDate DESC";
            TypedQuery<Order> query = enma.createQuery(jpql, Order.class);
            query.setParameter("userId", userId);
            return query.getResultList();
        } finally {
            enma.close();
        }
    }

    @Override
    public List<Order> findAll() {
        EntityManager enma = JpaConfig.getEntityManager();
        try {
            String jpql = "SELECT DISTINCT o FROM Order o "
                    + "LEFT JOIN FETCH o.orderDetails od "
                    + "LEFT JOIN FETCH od.product "
                    + "ORDER BY o.orderDate DESC";
            TypedQuery<Order> query = enma.createQuery(jpql, Order.class);
            return query.getResultList();
        } finally {
            enma.close();
        }
    }
}