package com.rewardwise.api.controller;

import com.rewardwise.api.dto.RewardResponse;
import com.rewardwise.api.entity.TransactionEntity;
import com.rewardwise.api.service.RewardService;

import tools.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;

import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RewardController.class)
public class RewardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoSpyBean
    private RewardService rewardService;

    @Autowired
    private ObjectMapper objectMapper;

    private List<TransactionEntity> transactions;
    private List<RewardResponse> responseList;

    @BeforeEach
    void setup() {
        TransactionEntity txn = new TransactionEntity();
        txn.setCustomerId("C1");
        txn.setAmount(120.0);
        txn.setDate(java.time.LocalDate.now());

        transactions = List.of(txn);

        Map<String, Integer> monthlyPoints = new HashMap<>();
        monthlyPoints.put("2024-01", 90);

        RewardResponse response = new RewardResponse("C1", monthlyPoints, 90);
        responseList = List.of(response);
    }

    @Test
    void shouldReturnRewardsForValidInput() throws Exception {
        Mockito.when(rewardService.calculateRewards(Mockito.any(), Mockito.any()))
                .thenReturn(responseList);

        mockMvc.perform(post("/api/rewards")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(transactions)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].customerId").value("C1"))
                .andExpect(jsonPath("$[0].totalPoints").value(90));
    }

	
	  @Test void shouldHandleEmptyTransactionList() throws Exception {
	  Mockito.when(rewardService.calculateRewards(Mockito.any(), Mockito.any()))
	  .thenReturn(Collections.emptyList());
	  
	  mockMvc.perform(post("/api/rewards") .contentType(MediaType.APPLICATION_JSON)
	  .content(objectMapper.writeValueAsString(Collections.emptyList())))
	  .andExpect(status().isOk()) .andExpect(content().json("[]")); }
	 

    @Test
    void shouldHandleNullBody() throws Exception {
        mockMvc.perform(post("/api/rewards")
                .contentType(MediaType.APPLICATION_JSON)
                .content(""))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldHandleMalformedJson() throws Exception {
        mockMvc.perform(post("/api/rewards")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ invalid json }"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldHandleServiceException() throws Exception {
        Mockito.when(rewardService.calculateRewards(Mockito.any(), Mockito.any()))
                .thenThrow(new RuntimeException("Service failure"));

        mockMvc.perform(post("/api/rewards")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(transactions)))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void shouldHandleLargeInput() throws Exception {
        List<TransactionEntity> largeList = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            TransactionEntity txn = new TransactionEntity();
            txn.setCustomerId("C" + i);
            txn.setAmount(200.0);
            txn.setDate(java.time.LocalDate.now());
            largeList.add(txn);
        }

        Mockito.when(rewardService.calculateRewards(Mockito.any(), Mockito.any()))
                .thenReturn(Collections.emptyList());

        mockMvc.perform(post("/api/rewards")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(largeList)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldHandleZeroAmountTransaction() throws Exception {
        transactions.get(0).setAmount(0.0);

        Mockito.when(rewardService.calculateRewards(Mockito.any(), Mockito.any()))
                .thenReturn(responseList);

        mockMvc.perform(post("/api/rewards")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(transactions)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldHandleNegativeAmountTransaction() throws Exception {
        transactions.get(0).setAmount(-50.0);

        Mockito.when(rewardService.calculateRewards(Mockito.any(), Mockito.any()))
                .thenReturn(responseList);

        mockMvc.perform(post("/api/rewards")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(transactions)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldHandleMissingFields() throws Exception {
        String json = "[{\"amount\":100.0}]"; // missing customerId & date

        Mockito.when(rewardService.calculateRewards(Mockito.any(), Mockito.any()))
                .thenReturn(responseList);

        mockMvc.perform(post("/api/rewards")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());
    }
}