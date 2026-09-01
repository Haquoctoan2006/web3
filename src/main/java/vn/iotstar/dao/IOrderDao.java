package vn.iotstar.dao;

import java.util.List;

import vn.iotstar.entity.Order;

public interface IOrderDao {

    void insert(Order order);

    Order findById(int id);

    List<Order> findByUser(int userId);

    List<Order> findAll();
}
