package com.example.interview.models;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "sales")
public class Sale {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Client client;

    @DateTimeFormat(pattern = "dd/MM/yyyy") //дд/мм/гггг
    @Column(name = "date_of_sale")
    private LocalDate dateOfSale;

    @Column(name = "bill_number")
    private String billNumber;

    @OneToMany(mappedBy = "sale")
    private List<Order> orders;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private SaleStatus status = SaleStatus.OFFER;

    public Sale() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public LocalDate getDateOfSale() {
        return dateOfSale;
    }

    public void setDateOfSale(LocalDate dateOfSale) {
        this.dateOfSale = dateOfSale;
    }

    public String getBillNumber() {
        return billNumber;
    }

    public void setBillNumber(String billNumber) {
        this.billNumber = billNumber;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public SaleStatus getStatus() {
        return status;
    }

    public void setStatus(SaleStatus status) {
        this.status = status;
    }

    public void fixedSale(LocalDate currentDate, int billNumber) {
        if (!getStatus().equals(SaleStatus.OFFER)) {
            throw new RuntimeException(String.format("sale with id %s doesnt have status 'OFFER'", getId()));
        }
        setDateOfSale(currentDate);
        setStatus(SaleStatus.FIX);
        setBillNumber(generateBillNumber(billNumber));
    }

    private String generateBillNumber(int billNumber) {
        return String.format("%06d", billNumber);
    }
}
