package vn.iotstar.entity;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.*;

@Entity
@Table(name = "products")
@NamedQuery(name = "Product.findAll", query = "SELECT p FROM Product p ORDER BY p.createdDate DESC")
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "productId")
    private int productId;

    @Column(name = "productName", columnDefinition = "VARCHAR(255)")
    private String productName;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    // Gia ban cho khach hang
    @Column(name = "price")
    private double price;

    // Gia nhap / gia von, chi Admin xem duoc, dung de tinh loi nhuan
    // Dung Double (wrapper) thay vi double (primitive) vi cot nay co the NULL trong DB
    @Column(name = "importPrice")
    private Double importPrice;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "image", columnDefinition = "VARCHAR(255)")
    private String image;

    @Column(name = "status")
    private int status;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createdDate")
    private Date createdDate;

    // bi-directional many-to-one association to Category
    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Category category;

    public Product() {
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getImportPrice() {
        return importPrice == null ? 0.0 : importPrice;
    }

    public void setImportPrice(Double importPrice) {
        this.importPrice = importPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}