package com.rewardwise.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class RewardProperties {
    
	  @Value("${reward.points.first-threshold}")
	    private int firstThreshold;

	    @Value("${reward.points.second-threshold}")
	    private int secondThreshold;

	    @Value("${reward.points.first-multiplier}")
	    private int firstMultiplier;

	    @Value("${reward.points.second-multiplier}")
	    private int secondMultiplier;
    public int getFirstThreshold() {
		return firstThreshold;
	}
	
	public int getSecondThreshold() {
		return secondThreshold;
	}
	
	public int getFirstMultiplier() {
		return firstMultiplier;
	}
	
	public int getSecondMultiplier() {
		return secondMultiplier;
	}
	

}