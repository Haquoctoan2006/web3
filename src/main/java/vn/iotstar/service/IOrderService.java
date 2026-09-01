package vn.iotstar.service;

import java.util.List;

import vn.iotstar.entity.Order;
import vn.iotstar.entity.User;

public interface IOrderService {

    /** Khach hang mua 1 san pham voi so luong chi dinh, tra ve Order da tao. */
    Order buyProduct(User user, int productId, int quantity) throws Exception;

    List<Order> findByUser(int userId);

    List<Order> findAll();
}
