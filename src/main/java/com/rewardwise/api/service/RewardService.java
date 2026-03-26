package com.rewardwise.api.service;

import com.rewardwise.api.dto.RewardResponse;


import java.time.LocalDate;
import java.util.List;

/**
 * Service interface for calculating reward points.
 */
public interface RewardService {

    /**
     * Calculates reward points per customer per month and total.
     *
     * @param transactions list of transactions
     * @return list of reward responses
     */

	List<RewardResponse> calculateRewards(LocalDate parse, LocalDate parse2);
}