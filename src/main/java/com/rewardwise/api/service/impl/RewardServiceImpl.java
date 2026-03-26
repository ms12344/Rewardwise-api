package com.rewardwise.api.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.rewardwise.api.dto.RewardResponse;
import com.rewardwise.api.entity.TransactionEntity;
import com.rewardwise.api.repository.TransactionRepository;
import com.rewardwise.api.service.RewardService;
import com.rewardwise.api.util.DateUtil;
import com.rewardwise.api.util.RewardCalculatorUtil;

/**
 * Service implementation for reward calculation.
 */
@Service
public class RewardServiceImpl implements RewardService {

    private final TransactionRepository repository;
    private final RewardCalculatorUtil rewardCalculatorUtil;

    public RewardServiceImpl(TransactionRepository repository,
                             RewardCalculatorUtil rewardCalculatorUtil) {
        this.repository = repository;
        this.rewardCalculatorUtil = rewardCalculatorUtil;
    }

    @Override
    public List<RewardResponse> calculateRewards(LocalDate start, LocalDate end) {

        List<TransactionEntity> transactions =
                repository.findByDateBetween(start, end);

        return transactions.stream()
                .collect(Collectors.groupingBy(
                        TransactionEntity::getCustomerId,
                        Collectors.groupingBy(
                                t -> DateUtil.getMonth(t.getDate()),
                                Collectors.summingInt(
                                        t -> rewardCalculatorUtil.calculatePoints(t.getAmount())
                                )
                        )
                ))
                .entrySet()
                .stream()
                .map(e -> new RewardResponse(
                        e.getKey(),
                        e.getValue(),
                        e.getValue().values().stream().mapToInt(Integer::intValue).sum()
                ))
                .toList();
    }
}