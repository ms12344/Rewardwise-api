package com.rewardwise.api.controller;



import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.rewardwise.api.controller.RewardController;
import com.rewardwise.api.service.RewardService;

@WebMvcTest(RewardController.class)
class RewardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RewardService service; 

    @Test
    void testAPI() throws Exception {

        Mockito.when(service.calculateRewards(Mockito.anyList()))
                .thenReturn(List.of());

        String json = """
        [
          {"customerId":"C1","amount":120,"date":"2026-01-10"}
        ]
        """;

        mockMvc.perform(post("/api/rewards")
                .contentType("application/json")
                .content(json))
                .andExpect(status().isOk());
    }
}
