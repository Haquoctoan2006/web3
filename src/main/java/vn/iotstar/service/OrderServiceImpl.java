package vn.iotstar.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import vn.iotstar.dao.IOrderDao;
import vn.iotstar.dao.IProductDao;
import vn.iotstar.dao.OrderDao;
import vn.iotstar.dao.ProductDao;
import vn.iotstar.entity.Order;
import vn.iotstar.entity.OrderDetail;
import vn.iotstar.entity.Product;
import vn.iotstar.entity.User;

public class OrderServiceImpl implements IOrderService {

    public IOrderDao orderDao = new OrderDao();
    public IProductDao productDao = new ProductDao();

    @Override
    public Order buyProduct(User user, int productId, int quantity) throws Exception {
        if (quantity <= 0) {
            throw new Exception("So luong mua phai lon hon 0");
        }

        Product product = productDao.findById(productId);
        if (product == null) {
            throw new Exception("San pham khong ton tai");
        }
        if (product.getQuantity() < quantity) {
            throw new Exception("San pham khong du so luong ton kho (con " + product.getQuantity() + ")");
        }

        // Tao don hang (gia ban duoc chot tai thoi diem mua, khong bi anh huong neu sau nay doi gia)
        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(new Date());
        order.setStatus(1); // coi nhu hoan tat ngay (khong lam quy trinh duyet don trong pham vi bai tap)
        order.setTotalAmount(product.getPrice() * quantity);

        OrderDetail detail = new OrderDetail();
        detail.setOrder(order);
        detail.setProduct(product);
        detail.setQuantity(quantity);
        detail.setPriceAtPurchase(product.getPrice());

        List<OrderDetail> details = new ArrayList<>();
        details.add(detail);
        order.setOrderDetails(details);

        orderDao.insert(order); // cascade = ALL nen OrderDetail duoc luu cung

        // Tru so luong ton kho
        product.setQuantity(product.getQuantity() - quantity);
        productDao.update(product);

        return order;
    }

    @Override
    public List<Order> findByUser(int userId) {
        return orderDao.findByUser(userId);
    }

    @Override
    public List<Order> findAll() {
        return orderDao.findAll();
    }
}
