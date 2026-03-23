package com.rewardwise.api.service;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.mockito.junit.jupiter.MockitoExtension;

import com.rewardwise.api.dto.RewardResponse;
import com.rewardwise.api.model.Transaction;
import com.rewardwise.api.service.RewardService;
import com.rewardwise.api.util.RewardCalculatorUtil;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class RewardServiceTest {
    @InjectMocks
    private RewardService service;
    @Mock
    private RewardCalculatorUtil rewardCalculatorUtil;
    @Test
    void testMultipleCustomersAndMonths() {

        List<Transaction> transactions = List.of(
                create("C1", 120, "2026-01-10"),
                create("C1", 75, "2026-01-15"),
                create("C2", 200, "2026-02-01")
        );

        List<RewardResponse> result = service.calculateRewards(transactions);

        assertEquals(2, result.size());
    }

    @Test
    void testEmptyTransactions() {
        assertTrue(service.calculateRewards(List.of()).isEmpty());
    }

    @Test
    void testInvalidCustomer() {
        Transaction t = new Transaction();
        t.setAmount(100);
        t.setDate(LocalDate.now());

        assertThrows(RuntimeException.class,
                () -> service.calculateRewards(List.of(t)));
    }

    private Transaction create(String c, double amt, String date) {
        Transaction t = new Transaction();
        t.setCustomerId(c);
        t.setAmount(amt);
        t.setDate(LocalDate.parse(date));
        return t;
    }
}