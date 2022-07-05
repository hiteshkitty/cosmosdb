package com.kits.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kits.cosmos.dto.MessageCountResponse;
import com.kits.cosmos.dto.MessageProcessed;
import com.kits.cosmos.dto.PendingTopicDetails;
import com.kits.repository.ConsumerDBRepository;

@Component
public class ConsumerService {

	Logger LOGGER = LoggerFactory.getLogger(ConsumerService.class);

	@Autowired
	ConsumerDBRepository consumerDBRepository;

	/**
	 * 
	 * @param topicName
	 * @param processingOrder
	 * @param partitionNumber
	 * @param startOffset
	 * @param endOffset
	 * @return
	 */
	public List<MessageCountResponse> getAllMessageCountWithOffset(String topicName, String processingOrder,
			int partitionNumber, int startOffset, int endOffset) {
		LOGGER.debug(
				"fetching all message count for ${topicName} and ${processorType} and ${partitionNumber} and ${startOffset} and ${endOffset}  "
						+ topicName,
				processingOrder, partitionNumber, startOffset, endOffset);
		List<MessageCountResponse> list = new ArrayList<>();
		list = consumerDBRepository.getAllMessageCountByTimeWithOffsetAppId(topicName, processingOrder, partitionNumber,
				startOffset, endOffset);
		return list;
	}

	public List<PendingTopicDetails> getPendingTopicDetails(String topicName, String processingOrder, int startOffset,
			int endOffset) {
		LOGGER.debug(
				"fetching all PendingTopicDetails count for ${topicName} and ${processorType} and ${startOffset} and ${endOffset}  "
						+ topicName,
				processingOrder, startOffset, endOffset);
		List<PendingTopicDetails> list = new ArrayList<>();
		list = consumerDBRepository.getPendingTopicDetails(topicName, processingOrder, startOffset, endOffset);
		return list;
	}

	public List<MessageProcessed> getAllProcessedMessage(String topicName, String consumerTopic, int partitionNumber,  int minOffset, int maxOffset) {
		List<MessageProcessed> list = new ArrayList<>();

		list = consumerDBRepository.getAllOffsetProcessed(topicName, consumerTopic, partitionNumber, minOffset, maxOffset);

		return list;
	}
}
