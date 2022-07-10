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
import com.kits.cosmos.dto.PendingTopicDetails;
import com.kits.repository.ConsumerDBRepository;
import com.kits.service.ConsumerService;

@ExtendWith(MockitoExtension.class)
public class ConsumerServiceTest {

	@InjectMocks
	private ConsumerService consumerService;

	@Mock
	private ConsumerDBRepository repo;

	@Test
	public void getConsumerMessagesWithTime() throws Exception {

		List<MessageCountResponse> countList = createResponse();

		String topicName = "testTopic1";
		String processingOrder = "MAIN";
		int partitionNumber = 1;
		int minOffset = 0;
		int maxOffset = 6;

		Mockito.when(repo.getAllMessageCountByTimeWithOffsetAppId(topicName, processingOrder, partitionNumber,
				minOffset, maxOffset)).thenReturn(countList);

		List<MessageCountResponse> response = consumerService.getAllMessageCountWithOffset(topicName, processingOrder,
				partitionNumber, minOffset, maxOffset);

		assertEquals(1, response.size());
		assertEquals(processingOrder, response.get(0).getProcessingOrder());

	}

	@Test
	public void getPendingTopicDetails() throws Exception {

		List<PendingTopicDetails> countList = createPendingTopicResponse();

		String topicName = "testTopic1";
		String processingOrder = "MAIN";
		int minOffset = 0;
		int maxOffset = 6;

		Mockito.when(repo.getPendingTopicDetails(topicName, processingOrder, minOffset, maxOffset))
				.thenReturn(countList);

		List<PendingTopicDetails> response = consumerService.getPendingTopicDetails(topicName, processingOrder,
				minOffset, maxOffset);

		assertEquals(1, response.size());
		assertEquals(processingOrder, response.get(0).getProcessingOrder());
	}

	@Test
	public void getAllProcessedMessage() throws Exception {

		List<MessageProcessed> countList = createProcessedMsgResponse();

		String topicName = "testTopic1";
		String consumerTopic = "EmployeeConsumerAppId";
		int partitionNumber = 1;
		int minOffset = 0;
		int maxOffset = 6;

		Mockito.when(repo.getAllOffsetProcessed(topicName, consumerTopic, partitionNumber, minOffset, maxOffset))
				.thenReturn(countList);

		List<MessageProcessed> response = consumerService.getAllProcessedMessage(topicName, consumerTopic,
				partitionNumber, minOffset, maxOffset);

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

	private List<PendingTopicDetails> createPendingTopicResponse() {

		List<PendingTopicDetails> response = new ArrayList<>();

		PendingTopicDetails pending = new PendingTopicDetails();
		pending.setConsumerAppId("EmployeeConsumerAppId");
		pending.setOffset(1);
		pending.setPartitionNumber(1);
		pending.setTopicName("testTopic1");
		pending.setProcessingOrder("MAIN");

		response.add(pending);
		return response;
	}

	private List<MessageCountResponse> createResponse() {

		List<MessageCountResponse> response = new ArrayList<MessageCountResponse>();

		MessageCountResponse count1 = new MessageCountResponse();
		count1.setConsumerAppId("EmployeeConsumerAppId");
		count1.setProcessingOrder("MAIN");
		count1.setMinOffset(0);
		count1.setMaxOffset(9);
		count1.setMessages(6);

		response.add(count1);

		return response;
	}
}
