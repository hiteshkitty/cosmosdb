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
import com.kits.dto.ApiResponse;
import com.kits.dto.ConsumerCountResponse;
import com.kits.dto.CountResponse;
import com.kits.dto.MessageCountResponse;
import com.kits.dto.ProducerData;
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

		List<List<ConsumerCountResponse>> list = new ArrayList<>();
		
		for (MessageCountResponse producerMessage : producerMessageList) {
			consumedMessageMainListTemp = consumerService.getAllMessageCountWithOffset(topicName, "MAIN",
					producerMessage.getPartitionNumber(), producerMessage.getMinOffset(),
					producerMessage.getMaxOffset());
			for (MessageCountResponse res : consumedMessageMainListTemp) {
				res.setPartitionNumber(producerMessage.getPartitionNumber());
				res.setProcessingOrder("MAIN");
			}
			
			consumerMessageRetryListTemp = consumerService.getAllMessageCountWithOffset(topicName, "retry",
					producerMessage.getPartitionNumber(), producerMessage.getMinOffset(),
					producerMessage.getMaxOffset());
			for (MessageCountResponse res : consumerMessageRetryListTemp) {
				res.setPartitionNumber(producerMessage.getPartitionNumber());
				res.setProcessingOrder("retry");
			}
			
			consumerMessageDeadLetterListTemp = consumerService.getAllMessageCountWithOffset(topicName, "dead-letter",
					producerMessage.getPartitionNumber(), producerMessage.getMinOffset(),
					producerMessage.getMaxOffset());
			for (MessageCountResponse res : consumerMessageDeadLetterListTemp) {
				res.setPartitionNumber(producerMessage.getPartitionNumber());
				res.setProcessingOrder("dead-letter");
			}
			
//			LOGGER.debug("consumedMessageMainList: " + consumedMessageMainListTemp);
//			LOGGER.debug("consumerMessageRetryList: " + consumerMessageRetryListTemp);
//			LOGGER.debug("consumerMessageDeadLetterList: " + consumerMessageDeadLetterListTemp);
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

	private AggregateResponse createAggregateResponse(String topicName, List<MessageCountResponse> producerMessageList,
			List<MessageCountResponse> consumedMessageMainList, List<MessageCountResponse> consumerMessageRetryList,
			List<MessageCountResponse> consumerMessageDeadLetterList, Long startTime, Long endTime) {

		AggregateResponse response = new AggregateResponse();
		List<AggregateConsumerResponse> list = new ArrayList<>();
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

			if (map.containsKey(appId)) {
				map.get(appId).setConsumed(map.get(appId).getConsumed() + consumedMessage.getMessages());
			} else {
				AggregateConsumerResponse con = new AggregateConsumerResponse();
				con.setAppId(appId);
				con.setConsumed(consumedMessage.getMessages());
				map.put(appId, con);
			}
		}

		for (MessageCountResponse retryMessage : consumerMessageRetryList) {
			String appId = retryMessage.getConsumerAppId();
			if (map.containsKey(appId)) {
				map.get(appId).setRetries(map.get(appId).getRetries() + retryMessage.getMessages());
			} else {
				AggregateConsumerResponse con = new AggregateConsumerResponse();
				con.setAppId(appId);
				con.setRetries(retryMessage.getMessages());
				map.put(appId, con);
			}
		}
		for (MessageCountResponse deadLetterMessage : consumerMessageDeadLetterList) {
			String appId = deadLetterMessage.getConsumerAppId();
			if (map.containsKey(appId)) {
				map.get(appId).setDeadLetter(map.get(appId).getDeadLetter() + deadLetterMessage.getMessages());
			} else {
				AggregateConsumerResponse con = new AggregateConsumerResponse();
				con.setAppId(appId);
				con.setDeadLetter(deadLetterMessage.getMessages());
				map.put(appId, con);
			}
		}
		System.out.println(map);
		
		for (Map.Entry<String,AggregateConsumerResponse> entry : map.entrySet()) {
            System.out.println("Key = " + entry.getKey() +
                             ", Value = " + entry.getValue());
            
            AggregateConsumerResponse res = new AggregateConsumerResponse();
            
            res.setAppId(entry.getKey());
            res.setConsumed(entry.getValue().getConsumed());
            res.setRetries(entry.getValue().getRetries());
            res.setDeadLetter(entry.getValue().getDeadLetter());
            
            list.add(res);
		}
		return response;
	}

}
