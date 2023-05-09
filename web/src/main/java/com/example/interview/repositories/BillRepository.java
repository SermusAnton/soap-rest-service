package com.example.interview.repositories;

import com.example.interview.models.Bill;
import com.example.interview.models.BillId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface BillRepository extends JpaRepository<Bill, BillId> {
    Optional<Bill> findFirstByDateOfSaleOrderByNumberDesc(LocalDate dateOfSale);
}
