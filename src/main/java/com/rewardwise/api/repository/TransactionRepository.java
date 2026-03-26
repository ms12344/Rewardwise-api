package com.rewardwise.api.repository;

import com.rewardwise.api.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

/**
 * Repository for transactions.
 */
public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {

    List<TransactionEntity> findByDateBetween(LocalDate start, LocalDate end);
}