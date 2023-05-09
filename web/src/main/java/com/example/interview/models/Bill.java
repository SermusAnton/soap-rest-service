package com.example.interview.models;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@IdClass(BillId.class)
@Table(name = "bill")
public class Bill {

    @Id
    @DateTimeFormat(pattern = "dd/MM/yyyy") //дд/мм/гггг
    @Column(name = "date_of_sale")
    private LocalDate dateOfSale;

    @Id
    @Column(name = "number")
    private int number;

    public Bill () {}

    public LocalDate getDateOfSale() {
        return dateOfSale;
    }

    public void setDateOfSale(LocalDate dateOfSale) {
        this.dateOfSale = dateOfSale;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
