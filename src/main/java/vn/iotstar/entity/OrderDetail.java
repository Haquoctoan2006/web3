package vn.iotstar.entity;

import java.io.Serializable;

import jakarta.persistence.*;

@Entity
@Table(name = "order_details")
public class OrderDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orderDetailId")
    private int orderDetailId;

    @ManyToOne
    @JoinColumn(name = "orderId")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product;

    @Column(name = "quantity")
    private int quantity;

    // Gia ban tai thoi diem mua (luu lai de sau nay doi gia san pham khong anh huong don cu)
    @Column(name = "priceAtPurchase")
    private double priceAtPurchase;

    public OrderDetail() {
    }

    public int getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(int orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPriceAtPurchase() {
        return priceAtPurchase;
    }

    public void setPriceAtPurchase(double priceAtPurchase) {
        this.priceAtPurchase = priceAtPurchase;
    }

    public double getSubTotal() {
        return quantity * priceAtPurchase;
    }
}
