package com.example.interview.models;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class BillId implements Serializable {

    private LocalDate dateOfSale;

    private int number;

    public BillId() {

    }

    public BillId(LocalDate dateOfSale, int number) {
        this.dateOfSale = dateOfSale;
        this.number = number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BillId billId = (BillId) o;
        return number == billId.number && Objects.equals(dateOfSale, billId.dateOfSale);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dateOfSale, number);
    }
}
