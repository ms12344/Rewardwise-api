package com.rewardwise.api.util;

import org.springframework.stereotype.Component;

import com.rewardwise.api.config.RewardProperties;

@Component
public class RewardCalculatorUtil {

    private final RewardProperties props;

    public RewardCalculatorUtil(RewardProperties props) {
        this.props = props;
    }

    public int calculatePoints(double amount) {

        if (amount <= 0) {
            return 0; // safer than throwing unless required
        }

        int points = 0;

        double secondThreshold = props.getSecondThreshold();
        double firstThreshold = props.getFirstThreshold();

        if (amount > secondThreshold) {
            points += (int) ((amount - secondThreshold) * props.getSecondMultiplier());
            amount = secondThreshold;
        }

        if (amount > firstThreshold) {
            points += (int) ((amount - firstThreshold) * props.getFirstMultiplier());
        }

        return points;
    }
}