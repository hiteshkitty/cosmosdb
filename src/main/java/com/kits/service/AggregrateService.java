package com.kits.service;

import java.util.ArrayList;
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

	public List<AggregrateResponse> getAllMissedMessageCount(String topicName) {
		List<AggregrateResponse> response = new ArrayList<>();
		String processingOrder = "MAIN";

		LOGGER.debug("fetching all message count for ${topicName} " + topicName);
		
		List<MessageCountResponse> producerMessages = producerService.getAllMessageCount(topicName,processingOrder);
		
		List<MessageCountResponse> consumerMessages = consumerService.getAllMessageCount(topicName,processingOrder);
		
		System.out.println("Producer Messages ");
		System.out.println(producerMessages);
		System.out.println("*********************************************************************");
		System.out.println("Consumer Messages ");
		System.out.println(consumerMessages);
		
		response = createResponse(topicName, processingOrder, producerMessages, consumerMessages);
		
		return response;
	}

	private List<AggregrateResponse> createResponse(String topicName, String processingOrder,
			List<MessageCountResponse> producerMessages, List<MessageCountResponse> consumerMessages) {

		List<AggregrateResponse> list = new ArrayList<>();
		AggregrateResponse messageResponse = null;

		for(MessageCountResponse producerMessage: producerMessages) {
			
			for(MessageCountResponse consumerMessage: consumerMessages) {
			
				if(producerMessage.getPartitionNumber() == consumerMessage.getPartitionNumber()) {
					messageResponse = new AggregrateResponse();
					messageResponse.setTopicName(topicName);
					messageResponse.setProcessingOrder(processingOrder);
					messageResponse.setPartitionName(producerMessage.getPartitionNumber());
					messageResponse.setMissedMessages(producerMessage.getMessages() - consumerMessage.getMessages());
					
				}
			}
			
			list.add(messageResponse); 
			
		}
		
		return list;
	}
}
