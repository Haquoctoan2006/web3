package vn.iotstar.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "orders")
@NamedQuery(name = "Order.findAll", query = "SELECT o FROM Order o ORDER BY o.orderDate DESC")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orderId")
    private int orderId;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "orderDate")
    private Date orderDate;

    @Column(name = "totalAmount")
    private double totalAmount;

    // 0 = cho xu ly, 1 = da hoan tat
    @Column(name = "status")
    private int status;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderDetail> orderDetails;

    public Order() {
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }
}
