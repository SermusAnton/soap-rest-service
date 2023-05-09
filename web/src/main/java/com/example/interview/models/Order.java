package com.example.interview.models;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

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
    private BigDecimal initialTotalPrice;

    // Храним в копейках, делим на 100
    @Column(name = "end_total_price")
    private BigDecimal endTotalPrice;

    @Min(value = 0, message = "The discount2 must be positive")
    @Max(value = 100, message = "The discount2 must be less than 100")
    @Column(name = "total_discount")
    private BigDecimal totalDiscount;

    public Order() {
    }

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

    public BigDecimal getInitialTotalPrice() {
        return initialTotalPrice;
    }

    public void setInitialTotalPrice(BigDecimal initialTotalPrice) {
        this.initialTotalPrice = initialTotalPrice;
    }

    public BigDecimal getEndTotalPrice() {
        return endTotalPrice;
    }

    public void setEndTotalPrice(BigDecimal endTotalPrice) {
        this.endTotalPrice = endTotalPrice;
    }

    public BigDecimal getTotalDiscount() {
        return totalDiscount;
    }

    public void setTotalDiscount(BigDecimal totalDiscount) {
        if (totalDiscount.doubleValue() > 18d) {
            totalDiscount = BigDecimal.valueOf(18);
        }
        this.totalDiscount = totalDiscount;
    }

    public void increaseCount() {
        this.count = this.count + 1;
    }

    public void decreaseCount() {
        if (count == 0) {
            return;
        }
        this.count = this.count - 1;
    }

    public void calculateTotal() {
        Objects.requireNonNull(this.sale, "sale doesnt set");
        Client client = this.sale.getClient();
        Objects.requireNonNull(client, String.format("client doesnt set for sale with id %s", sale.getId()));
        Objects.requireNonNull(product, String.format("product doesnt set for sale with id %s", sale.getId()));
        if (!sale.getStatus().equals(SaleStatus.OFFER)) {
            throw new RuntimeException("Calculating total to able about offer");
        }

        setInitialTotalPrice(product.getPrice().multiply(new BigDecimal(count)));

        BigDecimal personalDiscount;
        if (count >= 5 && client.getDiscount2().intValue() != 0) {
            personalDiscount = client.getDiscount2();
        } else {
            personalDiscount = client.getDiscount1();
        }
        setTotalDiscount(personalDiscount.add(product.getDiscount()));

        setEndTotalPrice(initialTotalPrice.subtract(
                initialTotalPrice.multiply(totalDiscount
                                .divide(BigDecimal.valueOf(100), 4, RoundingMode.DOWN))).setScale(2, RoundingMode.DOWN));
    }
}
