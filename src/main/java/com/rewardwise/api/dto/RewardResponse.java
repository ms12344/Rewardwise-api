package com.rewardwise.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RewardResponse {
    public RewardResponse(String customerId2, Map<String, Integer> monthlyPoints2, int totalPoints2) {
    	super();
		this.customerId=customerId;
		this.monthlyPoints=monthlyPoints;
		this.totalPoints=totalPoints;
	}
	private String customerId;
    private Map<String, Integer> monthlyPoints;
    private int totalPoints;
}
