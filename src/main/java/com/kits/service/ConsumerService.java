package com.kits.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kits.cosmos.dto.MessageCountResponse;
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

}
