package com.kits.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.kits.cosmos.dto.MessageCountResponse;
import com.kits.cosmos.dto.MessageProcessed;
import com.kits.repository.ProducerDBRepository;
import com.kits.service.ProducerService;

@ExtendWith(MockitoExtension.class)
public class ProducerServiceTest {

	@InjectMocks
	private ProducerService producerService;

	@Mock
	private ProducerDBRepository repo;

	@Test
	public void getAllTopics() throws Exception {

		List<String> list = new ArrayList<>();
		list.add("testTopic1");
		list.add("testTopic2");

		Mockito.when(repo.findAllDistinctTopicNames()).thenReturn(list);

		// test
		List<String> response = producerService.findAllDistinctTopicNames();
		assertEquals(2, response.size());
		assertEquals("testTopic1", response.get(0));

	}

	@Test
	public void getProducerMessagesWithTime() throws Exception {

		List<MessageCountResponse> countList = createResponse();

		String topicName = "testTopic1";
		String processingOrder = "MAIN";
		Long startTime = 1212l;
		Long endTime = 1313l;

		Mockito.when(repo.getAllMessageCountWithTimeAppId(topicName, processingOrder, startTime, endTime))
				.thenReturn(countList);

		List<MessageCountResponse> response = producerService.getProducerMessagesWithTime(topicName, processingOrder,
				startTime, endTime);

		assertEquals(1, response.size());
		assertEquals(processingOrder, response.get(0).getProcessingOrder());

	}

	@Test
	public void getAllProcessedMessage() throws Exception {

		List<MessageProcessed> countList = createProcessedMsgResponse();

		String topicName = "testTopic1";
		Long startTime = 1212l;
		Long endTime = 1313l;

		Mockito.when(repo.getAllOffsetProcessed(topicName, startTime, endTime)).thenReturn(countList);

		List<MessageProcessed> response = producerService.getAllProcessedMessage(topicName, startTime, endTime);

		assertEquals(1, response.size());
		assertEquals(2, response.get(0).getOffset());

	}

	private List<MessageProcessed> createProcessedMsgResponse() {
		List<MessageProcessed> response = new ArrayList<>();

		MessageProcessed msg = new MessageProcessed();
		msg.setOffset(2);
		msg.setPartitionNumber(1);
		response.add(msg);
		return response;
	}

	private List<MessageCountResponse> createResponse() {

		List<MessageCountResponse> response = new ArrayList<MessageCountResponse>();

		MessageCountResponse count = new MessageCountResponse();
		count.setConsumerAppId("EmployeeConsumerAppId");
		count.setProcessingOrder("MAIN");
		count.setMinOffset(0);
		count.setMaxOffset(9);
		count.setMessages(6);

		response.add(count);

		return response;
	}

}
