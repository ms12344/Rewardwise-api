package com.rewardwise.api.service;

import com.rewardwise.api.config.RewardProperties;
import com.rewardwise.api.dto.RewardResponse;
import com.rewardwise.api.entity.TransactionEntity;
import com.rewardwise.api.repository.TransactionRepository;
import com.rewardwise.api.service.impl.RewardServiceImpl;
import com.rewardwise.api.util.RewardCalculatorUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit test for RewardServiceImpl
 */
class RewardServiceTest {

    private RewardServiceImpl service;
    private TransactionRepository repository;

    @BeforeEach
    void setup() {

        // ✅ Mock repository
        repository = mock(TransactionRepository.class);

        // ✅ Mock properties
        RewardProperties props = mock(RewardProperties.class);
        when(props.getFirstThreshold()).thenReturn(50);
        when(props.getSecondThreshold()).thenReturn(100);
        when(props.getFirstMultiplier()).thenReturn(1);
        when(props.getSecondMultiplier()).thenReturn(2);

        // ✅ Real util with mocked props
        RewardCalculatorUtil util = new RewardCalculatorUtil(props);

        // ✅ Inject into service
        service = new RewardServiceImpl(repository, util);
    }

    @Test
    void testCalculateRewards() {

        List<TransactionEntity> mockData = List.of(
                create("C1", 120, "2026-01-10"),
                create("C1", 75, "2026-01-15"),
                create("C2", 200, "2026-02-01")
        );

        // ✅ Mock repository response
        when(repository.findByDateBetween(any(), any()))
                .thenReturn(mockData);

        List<RewardResponse> result =
                service.calculateRewards(
                        LocalDate.parse("2026-01-01"),
                        LocalDate.parse("2026-03-31")
                );

        assertEquals(2, result.size());
    }

    @Test
    void testEmptyResult() {

        when(repository.findByDateBetween(any(), any()))
                .thenReturn(List.of());

        List<RewardResponse> result =
                service.calculateRewards(
                        LocalDate.now(),
                        LocalDate.now()
                );

        assertTrue(result.isEmpty());
    }

    @Test
    void testNegativeAmount() {

        List<TransactionEntity> mockData = List.of(
                create("C1", -10, "2026-01-10")
        );

        when(repository.findByDateBetween(any(), any()))
                .thenReturn(mockData);

        assertThrows(RuntimeException.class,
                () -> service.calculateRewards(
                        LocalDate.now(),
                        LocalDate.now()
                ));
    }

    private TransactionEntity create(String c, double amt, String date) {
        return new TransactionEntity(
                null,
                c,
                amt,
                LocalDate.parse(date)
        );
    }
}