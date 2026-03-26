package com.rewardwise.api; // outside controller package

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.rewardwise.api.controller.RewardController;
import com.rewardwise.api.service.RewardService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RewardIntegrationTests {

	@LocalServerPort
	private int port;
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RewardService service;
    
    @Autowired
    TestRestTemplate restTemplate;

    @Test
    void testAPI() {

        String url = "http://localhost:" + port +
                "/rewardwise/api/rewards?startDate=2026-01-01&endDate=2026-03-31";

        String response = restTemplate.getForObject(url, String.class);

        assertThat(response).isNotNull();
    }
    
	/*
	 * @Test void testAPI1() throws Exception {
	 * 
	 * when(service.calculateRewards(Mockito.any(), Mockito.any()))
	 * .thenReturn(List.of());
	 * 
	 * String json = """ [ {"customerId":"C1","amount":120,"date":"2026-01-10"} ]
	 * """;
	 * 
	 * mockMvc.perform(post("/api/rewards") .contentType("application/json")
	 * .content(json)) .andExpect(status().isOk()); }
	 */
    
}