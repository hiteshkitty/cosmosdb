package com.kits.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kits.cosmos.dto.MessageCountResponse;
import com.kits.cosmos.dto.MessageProcessed;
import com.kits.repository.ProducerDBRepository;

@Component
public class ProducerService {

	Logger LOGGER = LoggerFactory.getLogger(ProducerService.class);

	@Autowired
	ProducerDBRepository producerDBRepository;

	/**
	 * 
	 * @return
	 */
	public List<String> findAllDistinctTopicNames() {
		return producerDBRepository.findAllDistinctTopicNames();
	}

	public List<MessageCountResponse> getProducerMessagesWithTime(String topicName, String processingOrder,
			Long startTime, Long endTime) {
		LOGGER.debug("fetching all message count for ${topicName} and ${processorType} and ${startTime} and ${endTime}"
				+ topicName, processingOrder, startTime, endTime);
		List<MessageCountResponse> list = new ArrayList<>();
		list = producerDBRepository.getAllMessageCountWithTimeAppId(topicName, processingOrder, startTime, endTime);
		return list;
	}
	
	public List<MessageProcessed> getAllProcessedMessage(String topicName, long startTime, long endTime) {
		List<MessageProcessed> list = new ArrayList<>();

		list = producerDBRepository.getAllOffsetProcessed(topicName, startTime, endTime);

		return list;
	}
}
