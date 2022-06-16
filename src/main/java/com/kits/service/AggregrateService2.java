package com.kits.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kits.dto.AggregateConsumerResponse;
import com.kits.dto.AggregateCountResponse;
import com.kits.dto.AggregateProducerResponse;
import com.kits.dto.AggregateResponse;
import com.kits.dto.AggregrateResponse;
import com.kits.dto.ApiResponse;
import com.kits.dto.ConsumerCountResponse;
import com.kits.dto.ConsumerData;
import com.kits.dto.CountResponse;
import com.kits.dto.MessageCountResponse;
import com.kits.dto.ProducerData;
import com.kits.repository.ProducerDBRepository;

@Component
public class AggregrateService2 {

	@Autowired
	private ProducerService producerService;
	@Autowired
	private ConsumerService consumerService;

	Logger LOGGER = LoggerFactory.getLogger(AggregrateService2.class);

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
//			response = createResponse(response, topicName, producerMessages, consumedMessages, retyMessages,
//					deadLetterMessages);
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
	public ApiResponse getAllMissedMessageCountsWithTime(String topicName, Long startTime, Long endTime) {
		ApiResponse response = new ApiResponse();
		CountResponse countResponse = new CountResponse();
		String processingOrder = "MAIN";

		response.setCountResponse(countResponse);
		countResponse.setStartTime(startTime);
		countResponse.setEndTime(endTime);
		countResponse.setTopicName(topicName);

		ProducerData producer = new ProducerData();
		countResponse.setProducer(producer);

		LOGGER.debug("getAllMissedMessageCountsWithTime() fetching all message count for ${topicName} " + topicName);

//		List<MessageCountResponse> producerMessages = producerService.getAllMessageCountWithTime(topicName, processingOrder, startTime, endTime);
		List<MessageCountResponse> producerMessages = producerService.getAllMessageCountWithTimeAppId(topicName,
				processingOrder, startTime, endTime);

		List<ConsumerCountResponse> consumedMessages = null;

		processingOrder = "retry";
		List<ConsumerCountResponse> retyMessages = null;

		processingOrder = "dead-letter";
		List<ConsumerCountResponse> deadLetterMessages = null;
		List<List<ConsumerCountResponse>> list = new ArrayList<>();
		
		for (MessageCountResponse producerMessage : producerMessages) {
			consumedMessages = consumerService.getAllMessageCountByTimeWithOffsetLatest(topicName, "MAIN",
					producerMessage.getPartitionNumber(), producerMessage.getMinOffset(),
					producerMessage.getMaxOffset());
			for(ConsumerCountResponse res: consumedMessages) {
				res.setPartitionNumber(producerMessage.getPartitionNumber());
				res.setProcessingOrder("MAIN");
			}
			
			retyMessages = consumerService.getAllMessageCountByTimeWithOffsetLatest(topicName, "retry",
					producerMessage.getPartitionNumber(), producerMessage.getMinOffset(),
					producerMessage.getMaxOffset());
			for(ConsumerCountResponse res: retyMessages) {
				res.setPartitionNumber(producerMessage.getPartitionNumber());
				res.setProcessingOrder("retry");
			}
			
			deadLetterMessages = consumerService.getAllMessageCountByTimeWithOffsetLatest(topicName, "dead-letter",
					producerMessage.getPartitionNumber(), producerMessage.getMinOffset(),
					producerMessage.getMaxOffset());
			for(ConsumerCountResponse res: retyMessages) {
				res.setPartitionNumber(producerMessage.getPartitionNumber());
				res.setProcessingOrder("retry");
			}
			
			list.add(consumedMessages);
			list.add(retyMessages);
			list.add(deadLetterMessages);
			response = createResponse(response, topicName, producerMessages, consumedMessages, retyMessages,
					deadLetterMessages);
		}

		return response;
	}
	
	
	public List<List<ConsumerCountResponse>> getAllMissedMessageCountsWithTimeList(String topicName, Long startTime, Long endTime) {
		ApiResponse response = new ApiResponse();
		CountResponse countResponse = new CountResponse();
		String processingOrder = "MAIN";

		response.setCountResponse(countResponse);
		countResponse.setStartTime(startTime);
		countResponse.setEndTime(endTime);
		countResponse.setTopicName(topicName);

		ProducerData producer = new ProducerData();
		countResponse.setProducer(producer);

		LOGGER.debug("getAllMissedMessageCountsWithTime() fetching all message count for ${topicName} " + topicName);

//		List<MessageCountResponse> producerMessages = producerService.getAllMessageCountWithTime(topicName, processingOrder, startTime, endTime);
		List<MessageCountResponse> producerMessages = producerService.getAllMessageCountWithTimeAppId(topicName,
				processingOrder, startTime, endTime);

		List<ConsumerCountResponse> consumedMessages = null;

		processingOrder = "retry";
		List<ConsumerCountResponse> retyMessages = null;

		processingOrder = "dead-letter";
		List<ConsumerCountResponse> deadLetterMessages = null;
		List<List<ConsumerCountResponse>> list = new ArrayList<>();
		
		for (MessageCountResponse producerMessage : producerMessages) {
			consumedMessages = consumerService.getAllMessageCountByTimeWithOffsetLatest(topicName, "MAIN",
					producerMessage.getPartitionNumber(), producerMessage.getMinOffset(),
					producerMessage.getMaxOffset());
			for(ConsumerCountResponse res: consumedMessages) {
				res.setPartitionNumber(producerMessage.getPartitionNumber());
				res.setProcessingOrder("MAIN");
			}
			
			retyMessages = consumerService.getAllMessageCountByTimeWithOffsetLatest(topicName, "retry",
					producerMessage.getPartitionNumber(), producerMessage.getMinOffset(),
					producerMessage.getMaxOffset());
			for(ConsumerCountResponse res: retyMessages) {
				res.setPartitionNumber(producerMessage.getPartitionNumber());
				res.setProcessingOrder("retry");
			}
			
			deadLetterMessages = consumerService.getAllMessageCountByTimeWithOffsetLatest(topicName, "dead-letter",
					producerMessage.getPartitionNumber(), producerMessage.getMinOffset(),
					producerMessage.getMaxOffset());
			for(ConsumerCountResponse res: deadLetterMessages) {
				res.setPartitionNumber(producerMessage.getPartitionNumber());
				res.setProcessingOrder("dead-letter");
			}
			
			list.add(consumedMessages);
			list.add(retyMessages);
			list.add(deadLetterMessages);
			response = createResponse(response, topicName, producerMessages, consumedMessages, retyMessages,
					deadLetterMessages);
		}

		return list;
	}

	public AggregateResponse getMissedMessagesWithLatestTime(String topicName, Long startTime, Long endTime) {
		String processingOrder = "MAIN";
		AggregateResponse response = new AggregateResponse();
		List<MessageCountResponse> producerMessageList = new ArrayList<>();
		List<MessageCountResponse> consumedMessageMainList = new ArrayList<>();
		List<MessageCountResponse> consumerMessageRetryList = new ArrayList<>();
		List<MessageCountResponse> consumerMessageDeadLetterList = new ArrayList<>();

		List<MessageCountResponse> consumedMessageMainListTemp = new ArrayList<>();
		List<MessageCountResponse> consumerMessageRetryListTemp = new ArrayList<>();
		List<MessageCountResponse> consumerMessageDeadLetterListTemp = new ArrayList<>();

		producerMessageList = producerService.getProducerMessagesWithTime(topicName, processingOrder, startTime,
				endTime);
		System.out.println("This is Producer List ::" + producerMessageList);

		for (MessageCountResponse producerMessage : producerMessageList) {
			consumedMessageMainListTemp = consumerService.getAllMessageCountWithOffset(topicName, "MAIN",
					producerMessage.getPartitionNumber(), producerMessage.getMinOffset(),
					producerMessage.getMaxOffset());
			consumerMessageRetryListTemp = consumerService.getAllMessageCountWithOffset(topicName, "retry",
					producerMessage.getPartitionNumber(), producerMessage.getMinOffset(),
					producerMessage.getMaxOffset());
			consumerMessageDeadLetterListTemp = consumerService.getAllMessageCountWithOffset(topicName, "dead-letter",
					producerMessage.getPartitionNumber(), producerMessage.getMinOffset(),
					producerMessage.getMaxOffset());
			LOGGER.debug("consumedMessageMainList: " + consumedMessageMainListTemp);
			LOGGER.debug("consumerMessageRetryList: " + consumerMessageRetryListTemp);
			LOGGER.debug("consumerMessageDeadLetterList: " + consumerMessageDeadLetterListTemp);
			consumedMessageMainList.addAll(consumedMessageMainListTemp);
			consumerMessageRetryList.addAll(consumerMessageRetryListTemp);
			consumerMessageDeadLetterList.addAll(consumerMessageDeadLetterListTemp);

		}
		LOGGER.debug("FinalConsumedMessageMainList: " + consumedMessageMainList);
		LOGGER.debug("FinalConsumerMessageRetryList: " + consumerMessageRetryList);
		LOGGER.debug("FinalConsumerMessageDeadLetterList: " + consumerMessageDeadLetterList);

		response = createAggregateResponse(topicName, producerMessageList, consumedMessageMainList,
				consumerMessageRetryList, consumerMessageDeadLetterList, startTime, endTime);
		return response;
	}

//	/**
//	 * 
//	 * @param topicName
//	 * @param time
//	 * @return
//	 */
//	public AggregrateResponse getAllMissedMessageCountByTime(String topicName, TimeEnum time) {
//		AggregrateResponse response = new AggregrateResponse();
//		String processingOrder = "MAIN";
//
//		long timeValue = time.getValue();
//		LOGGER.debug("fetching all message count for ${topicName} for last ${time} mins" + topicName, time);
//
//		List<MessageCountResponse> producerMessages = producerService.getAllMessageCountByTime(topicName,
//				processingOrder, time);
//
////		for (MessageCountResponse producerMessage : producerMessages) {
////			consumedMessages = consumerService.getAllMessageCountByTimeWithOffset(topicName, processingOrder, producerMessage.getPartitionNumber(), producerMessage.getMinOffset(), producerMessage.getMaxOffset());
////		}
//
//		List<MessageCountResponse> consumedMessages = consumerService.getAllMessageCountByTime(topicName,
//				processingOrder, time);
//
//		processingOrder = "retry";
//		List<MessageCountResponse> retyMessages = consumerService.getAllMessageCountByTime(topicName, processingOrder,
//				time);
//
//		processingOrder = "dead-letter";
//		List<MessageCountResponse> deadLetterMessages = consumerService.getAllMessageCountByTime(topicName,
//				processingOrder, time);
//
////		response = createResponse(response, topicName, producerMessages, consumedMessages, retyMessages,
////				deadLetterMessages);
//
//		return response;
//	}

	private AggregateResponse createAggregateResponse(String topicName, List<MessageCountResponse> producerMessageList,
			List<MessageCountResponse> consumedMessageMainList, List<MessageCountResponse> consumerMessageRetryList,
			List<MessageCountResponse> consumerMessageDeadLetterList, Long startTime, Long endTime) {

		AggregateResponse response = new AggregateResponse();
		List<AggregateConsumerResponse> list = new ArrayList<>();
		AggregateConsumerResponse consumer = new AggregateConsumerResponse();
		list.add(consumer);
		AggregateCountResponse count = new AggregateCountResponse();

		AggregateProducerResponse producer = new AggregateProducerResponse();
		count.setProducer(producer);
		response.setConsumer(list);
		response.setCount(count);

		int totalProducedMessages = 0;

		Map<String, AggregateConsumerResponse> map = new HashMap<>();
		
		count.setTopicName(topicName);
		count.setStartTime(startTime);
		count.setEndTime(endTime);
		producer.setAppId("PRODUCER");
		for (MessageCountResponse producerMessage : producerMessageList) {
			producer.setProduced(producer.getProduced() + producerMessage.getMessages());
		}
		
		
		for (MessageCountResponse consumedMessage : consumedMessageMainList) {
			String appId = consumedMessage.getConsumerAppId();
			
			if(map.containsKey(appId)) {
				map.get(appId).setConsumed(totalProducedMessages);
			} else {
				AggregateConsumerResponse con = new AggregateConsumerResponse();
				con.setAppId(appId);
				con.setConsumed(consumedMessage.getMessages());
			}
			
//			consumer.setAppId(consumedMessage.getConsumerAppId());
//			consumer.setConsumed(consumedMessage.getMessages() + consumer.getConsumed());
		}

		for (MessageCountResponse retryMessage : consumerMessageRetryList) {
//			consumer.setAppId(retryMessage.getConsumerAppId());
//			consumer.setRetries(retryMessage.getMessages() + consumer.getRetries());

		}
		for (MessageCountResponse deadLetterMessage : consumerMessageDeadLetterList) {
//			consumer.setAppId(deadLetterMessage.getConsumerAppId());
//			consumer.setDeadLetter(deadLetterMessage.getMessages() + consumer.getDeadLetter());
		}
//            if (totalProducedMessages != (response.getConsumed() + response.getDeadLetter() + response.getRetries())) {
//                response.setNotConsumed(totalProducedMessages - (response.getConsumed() + response.getDeadLetter() + response.getRetries()));
//            } else {
//                response.setNotConsumed(0);
//            }

		return response;
	}

	private AggregrateResponse createResponse1(AggregrateResponse response, String topicName,
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

	private ApiResponse createResponse(ApiResponse response, String topicName,
			List<MessageCountResponse> producerMessageList, List<ConsumerCountResponse> consumedMessageMainList,
			List<ConsumerCountResponse> consumerMessageRetryList,
			List<ConsumerCountResponse> consumerMessageDeadLetterList) {
		for (MessageCountResponse producerMessage : producerMessageList) {
			response.getCountResponse().getProducer().setId(producerMessage.getConsumerAppId());
			response.getCountResponse().getProducer().setProduced(
					response.getCountResponse().getProducer().getProduced() + producerMessage.getMessages());
			;
		}
		List<ConsumerData> consumerDataList = new ArrayList<>();

		for (ConsumerCountResponse consumedMessage : consumedMessageMainList) {
			System.out.println("MAIN: " + consumedMessage.getConsumerAppId() + " " + consumedMessage.getMessages());
		}
		for (ConsumerCountResponse retryMessage : consumerMessageRetryList) {
			System.out.println("retry: " + retryMessage.getConsumerAppId() + " " + retryMessage.getMessages());
		}
		for (ConsumerCountResponse deadLetterMessage : consumerMessageDeadLetterList) {
			System.out.println(
					"Deadletter: " + deadLetterMessage.getConsumerAppId() + " " + deadLetterMessage.getMessages());
		}

		return response;
	}

}
