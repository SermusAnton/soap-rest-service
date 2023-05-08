package com.example.interview.models;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "clients")
public class Client {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters")
    @Column(name = "name")
    private String name;

    @Min(value = 0, message = "The discount1 must be positive")
    @Max(value = 100, message = "The discount1 must be less than 100")
    @Column(name = "discount1")
    private float discount1;

    @Min(value = 0, message = "The discount2 must be positive")
    @Max(value = 100, message = "The discount2 must be less than 100")
    @Column(name = "discount2")
    private float discount2;

    @OneToMany(mappedBy = "client")
    private List<Sale> sales;

    public Client() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getDiscount1() {
        return discount1;
    }

    public void setDiscount1(float discount1) {
        this.discount1 = discount1;
    }

    public double getDiscount2() {
        return discount2;
    }

    public void setDiscount2(float discount2) {
        this.discount2 = discount2;
    }

    public List<Sale> getSales() {
        return sales;
    }

    public void setSales(List<Sale> sales) {
        this.sales = sales;
    }
}
