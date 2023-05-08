package com.example.interview.models;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "sale_id", referencedColumnName = "id")
    private Sale sale;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    @Column(name = "count")
    private int count;

    // Храним в копейках, делим на 100
    @Column(name = "initial_total_price")
    private long initialTotalPrice;

    // Храним в копейках, делим на 100
    @Column(name = "end_total_price")
    private long endTotalPrice;

    @Min(value = 0, message = "The discount2 must be positive")
    @Max(value = 100, message = "The discount2 must be less than 100")
    @Column(name = "total_discount")
    private float totalDiscount;

    public Order() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Sale getSale() {
        return sale;
    }

    public void setSale(Sale sale) {
        this.sale = sale;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public long getInitialTotalPrice() {
        return initialTotalPrice;
    }

    public void setInitialTotalPrice(long initialTotalPrice) {
        this.initialTotalPrice = initialTotalPrice;
    }

    public long getEndTotalPrice() {
        return endTotalPrice;
    }

    public void setEndTotalPrice(long endTotalPrice) {
        this.endTotalPrice = endTotalPrice;
    }

    public float getTotalDiscount() {
        return totalDiscount;
    }

    public void setTotalDiscount(float totalDiscount) {
        this.totalDiscount = totalDiscount;
    }
}
