package com.rewardwise.api.controller;


import org.springframework.web.bind.annotation.*;

import com.rewardwise.api.dto.RewardResponse;
import com.rewardwise.api.service.RewardService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/rewards")
public class RewardController {

    private final RewardService service;

    public RewardController(RewardService service) {
        this.service = service;
    }
    @GetMapping
    public List<RewardResponse> getRewards(
            @RequestParam String startDate,
            @RequestParam String endDate) {

        return service.calculateRewards(
                LocalDate.parse(startDate),
                LocalDate.parse(endDate)
        );
    }
//    @PostMapping
//    public List<RewardResponse> calculate(@RequestBody List<Transaction> transactions) {
//        return service.calculateRewards(transactions);
//    }
}