package com.rewardwise.api.controller;


import org.springframework.web.bind.annotation.*;

import com.rewardwise.api.dto.RewardResponse;
import com.rewardwise.api.model.Transaction;
import com.rewardwise.api.service.RewardService;

import java.util.List;

@RestController
@RequestMapping("/api/rewards")
public class RewardController {

    private final RewardService service;

    public RewardController(RewardService service) {
        this.service = service;
    }

    @PostMapping
    public List<RewardResponse> calculate(@RequestBody List<Transaction> transactions) {
        return service.calculateRewards(transactions);
    }
}