package com.kits.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kits.dto.MessageCountResponse;
import com.kits.model.Consumer;
import com.kits.repository.ConsumerDBRepository;

@Component
public class ConsumerService {

	Logger LOGGER = LoggerFactory.getLogger(ConsumerService.class);

	@Autowired
	ConsumerDBRepository consumerDBRepository;

	public List<Consumer> getAllConsumers() {
		LOGGER.info("fetching all producers");
		return consumerDBRepository.getAllConsumers();
	}

	/**
	 * 
	 * @param topicName
	 * @param processingOrder
	 * @return
	 */
	public List<MessageCountResponse> getAllMessageCount(String topicName, String processingOrder) {
		LOGGER.debug("fetching all message count for ${topicName} and ${processorType} " + topicName, processingOrder);
		List<MessageCountResponse> list = new ArrayList<>();
		list = consumerDBRepository.getAllMessageCount(topicName, processingOrder);
		return list;
	}

	/**
	 * 
	 * @param topicName
	 * @param processingOrder
	 * @param time
	 * @return
	 */
	public List<MessageCountResponse> getAllMessageCountByTime(String topicName, String processingOrder,
			TimeEnum time) {
		LOGGER.debug("fetching all message count for ${topicName} and ${processorType} for ${time} mins" + topicName, processingOrder, time);
		List<MessageCountResponse> list = new ArrayList<>();
		list = consumerDBRepository.getAllMessageCountByTime(topicName, processingOrder, time.getValue());
		return list;
	}

}