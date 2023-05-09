package com.example.interview.repositories;

import com.example.interview.models.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {
    Optional<Sale> findByIdAndClientId(long id, long clientId);

    void deleteByIdAndClientId(long id, long clientId);
}
