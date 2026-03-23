package com.rewardwise.api.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rewardwise.api.dto.RewardResponse;
import com.rewardwise.api.model.Transaction;
import com.rewardwise.api.util.DateUtil;
import com.rewardwise.api.util.RewardCalculatorUtil;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RewardService {
	 
	 private RewardCalculatorUtil rewardCalculatorUtil;

    public RewardService(RewardCalculatorUtil rewardCalculatorUtil) {
        this.rewardCalculatorUtil = rewardCalculatorUtil;
    }

    public List<RewardResponse> calculateRewards(List<Transaction> transactions) {

        if (transactions == null || transactions.isEmpty()) {
            return Collections.emptyList();
        }

        // Step 1: Validate all transactions
        transactions.forEach(this::validate);

        // Step 2: Group by customer → month → sum points
        Map<String, Map<String, Integer>> groupedData =
                transactions.stream()
                        .collect(Collectors.groupingBy(
                                Transaction::getCustomerId,
                                Collectors.groupingBy(
                                        t -> DateUtil.getMonth(t.getDate()),
                                        Collectors.summingInt(
                                                t -> rewardCalculatorUtil.calculatePoints(t.getAmount())
                                        )
                                )
                        ));

        // Step 3: Convert to response DTO
        return groupedData.entrySet()
                .stream()
                .map(entry -> buildResponse(entry.getKey(), entry.getValue()))
                .toList();
    }

    private RewardResponse buildResponse(String customerId, Map<String, Integer> monthlyPoints) {

        int totalPoints = monthlyPoints.values()
                .stream()
                .mapToInt(Integer::intValue)
                .sum();

        return new RewardResponse(customerId, monthlyPoints, totalPoints);
    }

    private void validate(Transaction t) {

        if (t == null) {
            throw new RuntimeException("Transaction cannot be null");
        }

        if (t.getCustomerId() == null || t.getCustomerId().isBlank()) {
            throw new RuntimeException("CustomerId is required");
        }

        if (t.getDate() == null) {
            throw new RuntimeException("Transaction date is required");
        }

        if (t.getAmount() < 0) {
            throw new RuntimeException("Amount cannot be negative");
        }
    }
}
