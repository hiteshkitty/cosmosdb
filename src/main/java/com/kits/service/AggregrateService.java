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

		List<MessageCountResponse> consumedMessages = null;// consumerService.getAllMessageCount(topicName,
															// processingOrder);

		processingOrder = "retry";
		List<MessageCountResponse> retyMessages = null;// consumerService.getAllMessageCount(topicName,
														// processingOrder);

		processingOrder = "dead-letter";
		List<MessageCountResponse> deadLetterMessages = null;// consumerService.getAllMessageCount(topicName,
																// processingOrder);

		for (MessageCountResponse producerMessage : producerMessages) {
			consumedMessages = consumerService.getAllMessageCountByTimeWithOffset(topicName, "MAIN",
					producerMessage.getPartitionNumber(), producerMessage.getMinOffset(),
					producerMessage.getMaxOffset());
			retyMessages = consumerService.getAllMessageCountByTimeWithOffset(topicName, "retry",
					producerMessage.getPartitionNumber(), producerMessage.getMinOffset(),
					producerMessage.getMaxOffset());
			deadLetterMessages = consumerService.getAllMessageCountByTimeWithOffset(topicName, "dead-letter",
					producerMessage.getPartitionNumber(), producerMessage.getMinOffset(),
					producerMessage.getMaxOffset());
			response = createResponse(response, topicName, producerMessages, consumedMessages, retyMessages,
					deadLetterMessages);
		}

		return response;
	}

	/**
	 * 
	 * @param topicName
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public AggregrateResponse getAllMissedMessageCountsWithTime(String topicName, Long startTime, Long endTime) {
		AggregrateResponse response = new AggregrateResponse();
		String processingOrder = "MAIN";
		response.setStartTime(startTime);
		response.setEndTime(endTime);

		LOGGER.debug("getAllMissedMessageCountsWithTime() fetching all message count for ${topicName} " + topicName);

		List<MessageCountResponse> producerMessages = producerService.getAllMessageCountWithTime(topicName, processingOrder, startTime, endTime);

		List<MessageCountResponse> consumedMessages = null;

		processingOrder = "retry";
		List<MessageCountResponse> retyMessages = null;

		processingOrder = "dead-letter";
		List<MessageCountResponse> deadLetterMessages = null;

		for (MessageCountResponse producerMessage : producerMessages) {
			consumedMessages = consumerService.getAllMessageCountByTimeWithOffset(topicName, "MAIN",
					producerMessage.getPartitionNumber(), producerMessage.getMinOffset(),
					producerMessage.getMaxOffset());
			retyMessages = consumerService.getAllMessageCountByTimeWithOffset(topicName, "retry",
					producerMessage.getPartitionNumber(), producerMessage.getMinOffset(),
					producerMessage.getMaxOffset());
			deadLetterMessages = consumerService.getAllMessageCountByTimeWithOffset(topicName, "dead-letter",
					producerMessage.getPartitionNumber(), producerMessage.getMinOffset(),
					producerMessage.getMaxOffset());
			response = createResponse(response, topicName, producerMessages, consumedMessages, retyMessages,
					deadLetterMessages);
		}

		return response;
	}

	/**
	 * 
	 * @param topicName
	 * @param time
	 * @return
	 */
	public AggregrateResponse getAllMissedMessageCountByTime(String topicName, TimeEnum time) {
		AggregrateResponse response = new AggregrateResponse();
		String processingOrder = "MAIN";

		long timeValue = time.getValue();
		LOGGER.debug("fetching all message count for ${topicName} for last ${time} mins" + topicName, time);

		List<MessageCountResponse> producerMessages = producerService.getAllMessageCountByTime(topicName,
				processingOrder, time);

//		for (MessageCountResponse producerMessage : producerMessages) {
//			consumedMessages = consumerService.getAllMessageCountByTimeWithOffset(topicName, processingOrder, producerMessage.getPartitionNumber(), producerMessage.getMinOffset(), producerMessage.getMaxOffset());
//		}

		List<MessageCountResponse> consumedMessages = consumerService.getAllMessageCountByTime(topicName,
				processingOrder, time);

		processingOrder = "retry";
		List<MessageCountResponse> retyMessages = consumerService.getAllMessageCountByTime(topicName, processingOrder,
				time);

		processingOrder = "dead-letter";
		List<MessageCountResponse> deadLetterMessages = consumerService.getAllMessageCountByTime(topicName,
				processingOrder, time);

		response = createResponse(response, topicName, producerMessages, consumedMessages, retyMessages,
				deadLetterMessages);

		return response;
	}

	private AggregrateResponse createResponse(AggregrateResponse response, String topicName,
			List<MessageCountResponse> producerMessageList, List<MessageCountResponse> consumedMessageMainList,
			List<MessageCountResponse> consumerMessageRetryList,
			List<MessageCountResponse> consumerMessageDeadLetterList) {
		for (MessageCountResponse producerMessage : producerMessageList) {
			for (MessageCountResponse consumedMessage : consumedMessageMainList) {
				if (producerMessage.getPartitionNumber() == consumedMessage.getPartitionNumber()) {
					response.setTopicName(topicName);
					response.setProduced(response.getProduced() + producerMessage.getMessages());
					response.setConsumed(response.getConsumed() + consumedMessage.getMessages());
					break;
				}
			}
			for (MessageCountResponse retryMessage : consumerMessageRetryList) {
				if (producerMessage.getPartitionNumber() == retryMessage.getPartitionNumber()) {
					response.setRetries(response.getRetries() + retryMessage.getMessages());
					break;
				}
			}
			for (MessageCountResponse deadLetterMessage : consumerMessageDeadLetterList) {
				if (producerMessage.getPartitionNumber() == deadLetterMessage.getPartitionNumber()) {
					response.setDeadLetter(response.getDeadLetter() + deadLetterMessage.getMessages());
					break;
				}
			}

		}
		return response;
	}

}
