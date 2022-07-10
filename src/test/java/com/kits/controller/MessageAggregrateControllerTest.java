package com.kits.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kits.cosmos.dto.AggregateCountResponse;
import com.kits.cosmos.dto.AggregateResponse;
import com.kits.cosmos.dto.DateRangeRequest;
import com.kits.cosmos.dto.ProducerResponse;
import com.kits.cosmos.dto.TopicNameResponse;
import com.kits.service.AggregrateService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(MessageAggregrateController.class)

public class MessageAggregrateControllerTest {

	@MockBean
	AggregrateService service;

	@Autowired
	MockMvc mockMvc;

	@Test
	public void testfindAll() throws Exception {
		ProducerResponse response = new ProducerResponse();
		List<TopicNameResponse> topicNames = Arrays.asList(new TopicNameResponse("testTopic1"),
				new TopicNameResponse("testTopic2"));
		response.setTopics(topicNames);

		Mockito.when(service.getAllTopics()).thenReturn(response);

		mockMvc.perform(get("/api/v1/aggregrate/getalltopics")).andExpect(status().isOk())
				.andExpect(jsonPath("$.topics", Matchers.hasSize(2)))
				.andExpect(jsonPath("$.topics[0].topicName", Matchers.is("testTopic1")));
	}

	@Test
	public void getAllMissedMessageCountsWithTime() throws Exception {
		AggregateResponse response = createResponse();

		String topicName = "testTopic1";
		DateRangeRequest request = new DateRangeRequest();
		request.setStartDate("17-01-2020");
		request.setEndDate("22-07-2022");
		request.setStartTime("00:58");
		request.setEndTime("11:58");

		Mockito.when(service.getMissedMessagesWithLatestTime(topicName, 1579202880000l, 1658471280000l))
				.thenReturn(response);

		mockMvc.perform(post("/api/v1/aggregrate/getmissedmessages/topicname/{topicName}", topicName)
				.content(asJsonString(request)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.count.topicName", Matchers.is("testTopic1")));

	}

//	@Test
//	public void getAllPendingMessages() throws Exception {
//		Map<Integer, List<Integer>> response = createMapResponse();
//		
//		String topicName = "testTopic1";
//		String consumerTopic = "testConsumer";
//		
//		DateRangeRequest request = new DateRangeRequest();
//		request.setStartDate("17-01-2020");
//		request.setEndDate("22-07-2022");
//		request.setStartTime("00:58");
//		request.setEndTime("11:58");
//
//		Mockito.when(service.getAllPendingMessages(topicName, consumerTopic, 1579202880000l, 1658471280000l)).thenReturn(response);
//
//		mockMvc.perform(post("/api/v1/aggregrate/getpendingmessages/topicname/{topicName}/consumertopic/{consumerTopic}", topicName, consumerTopic)
//				.content(asJsonString(request)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
//				.andExpect(jsonPath("$.0").value(hasSize(3)));
//
//	}

	private Map<Integer, List<Integer>> createMapResponse() {
		Map<Integer, List<Integer>> response = new HashMap<>();

		response.put(0, Arrays.asList(1, 2, 3));
		response.put(1, Arrays.asList(5, 6, 7));
		return response;
	}

	private AggregateResponse createResponse() {

		AggregateResponse response = new AggregateResponse();

		AggregateCountResponse countResponse = new AggregateCountResponse();

		countResponse.setTopicName("testTopic1");
		countResponse.setStart("2020/01/17 12:58 AM");
		countResponse.setEnd("2022/07/22 11:58 AM");

		response.setCount(countResponse);
		return response;
	}

	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
