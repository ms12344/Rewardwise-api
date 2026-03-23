package com.rewardwise.api.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.rewardwise.api.config.RewardProperties;
import com.rewardwise.api.util.RewardCalculatorUtil;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class RewardCalculatorUtilTest {

    private RewardCalculatorUtil util;

    @BeforeEach
    void setup() {
        RewardProperties props = mock(RewardProperties.class);

        when(props.getFirstThreshold()).thenReturn(50);
        when(props.getSecondThreshold()).thenReturn(100);
        when(props.getFirstMultiplier()).thenReturn(1);
        when(props.getSecondMultiplier()).thenReturn(2);

        util = new RewardCalculatorUtil(props);
    }

    @Test
    void testAmountAbove100() {
        assertEquals(90, util.calculatePoints(120));
    }
}
