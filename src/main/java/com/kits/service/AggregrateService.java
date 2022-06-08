package com.kits.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kits.dto.AggregrateResponse;
import com.kits.dto.MessageCountResponse;
import com.kits.repository.ProducerDBRepository;

@Component
public class AggregrateService {

	@Autowired
	private ProducerService producerService;
	@Autowired
	private ConsumerService consumerService;

	Logger LOGGER = LoggerFactory.getLogger(AggregrateService.class);

	@Autowired
	ProducerDBRepository producerDBRepository;

	public AggregrateResponse getAllMissedMessageCount(String topicName) {
		AggregrateResponse response = new AggregrateResponse();
		String processingOrder = "MAIN";

		LOGGER.debug("fetching all message count for ${topicName} " + topicName);

		List<MessageCountResponse> producerMessages = producerService.getAllMessageCount(topicName, processingOrder);

		List<MessageCountResponse> consumedMessages = consumerService.getAllMessageCount(topicName, processingOrder);

		processingOrder = "retry";
		List<MessageCountResponse> retyMessages = consumerService.getAllMessageCount(topicName, processingOrder);

		processingOrder = "dead-letter";
		List<MessageCountResponse> deadLetterMessages = consumerService.getAllMessageCount(topicName, processingOrder);

		System.out.println("Producer Messages ");
		System.out.println(producerMessages);
		System.out.println("*********************************************************************");
		System.out.println("Consumer Messages ");
		System.out.println(consumedMessages);
		System.out.println("retyMessages Messages ");
		System.out.println(retyMessages);
		System.out.println("deadLetterMessages Messages ");
		System.out.println(deadLetterMessages);

		response = createResponse(topicName, producerMessages, consumedMessages, retyMessages, deadLetterMessages);

		return response;
	}
	
	public AggregrateResponse getAllMissedMessageCountByTime(String topicName, TimeEnum time) {
		AggregrateResponse response = new AggregrateResponse();
		String processingOrder = "MAIN";

		long timeValue = time.getValue();
		LOGGER.debug("fetching all message count for ${topicName} for last ${time} mins" + topicName, time);

		List<MessageCountResponse> producerMessages = producerService.getAllMessageCountByTime(topicName, processingOrder, time);

		List<MessageCountResponse> consumedMessages = consumerService.getAllMessageCountByTime(topicName, processingOrder, time);

		processingOrder = "retry";
		List<MessageCountResponse> retyMessages = consumerService.getAllMessageCountByTime(topicName, processingOrder, time);

		processingOrder = "dead-letter";
		List<MessageCountResponse> deadLetterMessages = consumerService.getAllMessageCountByTime(topicName, processingOrder, time);

//		System.out.println("Producer Messages ");
//		System.out.println(producerMessages);
//		System.out.println("*********************************************************************");
//		System.out.println("Consumer Messages ");
//		System.out.println(consumedMessages);
//		System.out.println("retyMessages Messages ");
//		System.out.println(retyMessages);
//		System.out.println("deadLetterMessages Messages ");
//		System.out.println(deadLetterMessages);

		response = createResponse(topicName, producerMessages, consumedMessages, retyMessages, deadLetterMessages);

		return response;
	}
	

	private AggregrateResponse createResponse(String topicName, List<MessageCountResponse> producerMessageList,
			List<MessageCountResponse> consumedMessageMainList, List<MessageCountResponse> consumerMessageRetryList,
			List<MessageCountResponse> consumerMessageDeadLetterList) {
		AggregrateResponse response = new AggregrateResponse();
		int totalProducedMessages = 0;
		for (MessageCountResponse producerMessage : producerMessageList) {
			response = new AggregrateResponse();
			for (MessageCountResponse consumedMessage : consumedMessageMainList) {
				if (producerMessage.getPartitionNumber() == consumedMessage.getPartitionNumber()) {
					response.setTopicName(topicName);
					response.setProduced(producerMessage.getMessages());
					response.setConsumed(consumedMessage.getMessages());
					totalProducedMessages = producerMessage.getMessages();
					break;
				}
			}
			for (MessageCountResponse retryMessage : consumerMessageRetryList) {
				if (producerMessage.getPartitionNumber() == retryMessage.getPartitionNumber()) {
					response.setRetries(retryMessage.getMessages());
					break;
				}
			}
			for (MessageCountResponse deadLetterMessage : consumerMessageDeadLetterList) {
				if (producerMessage.getPartitionNumber() == deadLetterMessage.getPartitionNumber()) {
					response.setDeadLetter(deadLetterMessage.getMessages());
					break;
				}
			}

		}
		return response;
	}
}
