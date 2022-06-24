package com.kits.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kits.cosmos.dto.AggregateConsumerResponse;
import com.kits.cosmos.dto.AggregateCountResponse;
import com.kits.cosmos.dto.AggregateProducerResponse;
import com.kits.cosmos.dto.AggregateResponse;
import com.kits.cosmos.dto.MessageCountResponse;
import com.kits.cosmos.dto.PendingTopicDetails;
import com.kits.cosmos.dto.ProducerResponse;
import com.kits.cosmos.dto.TopicNameResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AggregrateService {

	@Autowired
	private ProducerService producerService;
	@Autowired
	private ConsumerService consumerService;

	public static final String PO_MAIN = "MAIN";
	public static final String PO_RETRY = "RETRY";
	public static final String PO_DEAD_LETTER = "DEAD-LETTER";

	public ProducerResponse getAllTopics() {
		List<String> topicList = producerService.findAllDistinctTopicNames();
		ProducerResponse response = new ProducerResponse();
		response.setTopics(new ArrayList<>());
		for (String topicName : topicList) {
			response.getTopics().add(new TopicNameResponse(topicName));
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
	public AggregateResponse getMissedMessagesWithLatestTime(String topicName, Long startTime, Long endTime) {

		List<MessageCountResponse> producerMessageList;
		List<MessageCountResponse> consumedMessageMainList = new ArrayList<>();
		List<MessageCountResponse> consumerMessageRetryList = new ArrayList<>();
		List<MessageCountResponse> consumerMessageDeadLetterList = new ArrayList<>();

		producerMessageList = producerService.getProducerMessagesWithTime(topicName, PO_MAIN, startTime, endTime);
		log.debug("This is Producer List ::" + producerMessageList);

		for (MessageCountResponse producerMessage : producerMessageList) {
			consumedMessageMainList.addAll(getAllMsgCountWithOffset(producerMessage, topicName, PO_MAIN));
			consumerMessageRetryList.addAll(getAllMsgCountWithOffset(producerMessage, topicName, PO_RETRY));
			consumerMessageDeadLetterList.addAll(getAllMsgCountWithOffset(producerMessage, topicName, PO_DEAD_LETTER));
		}

		log.debug("FinalConsumedMessageMainList: " + consumedMessageMainList.size());
		log.debug("FinalConsumerMessageRetryList: " + consumerMessageRetryList.size());
		log.debug("FinalConsumerMessageDeadLetterList: " + consumerMessageDeadLetterList.size());

		AggregateCountResponse agrCntResp = getAggregateCountResponse(topicName, startTime, endTime);
		return createAggregateResponse( producerMessageList, consumedMessageMainList,
				consumerMessageRetryList, consumerMessageDeadLetterList, agrCntResp);
	}

	private List<MessageCountResponse> getAllMsgCountWithOffset(
			MessageCountResponse producerMessage, String topicName, String processingOrd) {
		List<MessageCountResponse> consumedMessageList = consumerService.getAllMessageCountWithOffset(
				topicName, processingOrd, producerMessage.getPartitionNumber(),
				producerMessage.getMinOffset(), producerMessage.getMaxOffset());
		for (MessageCountResponse res : consumedMessageList) {
			res.setPartitionNumber(producerMessage.getPartitionNumber());
			res.setProcessingOrder(processingOrd);
		}
		log.debug(processingOrd + " consumedMessageList: " + consumedMessageList.size());
		return consumedMessageList;
	}

	private AggregateResponse createAggregateResponse(List<MessageCountResponse> producerMessageList,
													  List<MessageCountResponse> consumedMessageMainList, List<MessageCountResponse> consumerMessageRetryList,
													  List<MessageCountResponse> consumerMessageDeadLetterList, AggregateCountResponse agrCntResp) {

		int prodMsgCount = 0;
		Map<String, AggregateConsumerResponse> consumerMap = new HashMap<>();
		Map<String, AggregateProducerResponse> producerMap = new HashMap<>();
		prodMsgCount = updateProducerMap( producerMessageList, producerMap, prodMsgCount);
		updateConsumerMap(consumedMessageMainList, consumerMap, PO_MAIN);
		updateConsumerMap(consumerMessageRetryList, consumerMap, PO_RETRY);
		updateConsumerMap(consumerMessageDeadLetterList, consumerMap, PO_DEAD_LETTER);

		log.debug(consumerMap.toString());

		for (Map.Entry<String, AggregateProducerResponse> entry : producerMap.entrySet()) {
			log.debug("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			AggregateProducerResponse res = AggregateProducerResponse.builder()
					.appId(entry.getKey())
					.produced(entry.getValue().getProduced()).build();
			agrCntResp.getProducer().add(res);
		}
		

		for (Map.Entry<String, AggregateConsumerResponse> entry : consumerMap.entrySet()) {
			List<PendingTopicDetails> pendingTopicDetailsList = new ArrayList<>();
			PendingTopicDetails pendingTopicDetails = new PendingTopicDetails();
			pendingTopicDetails.setOffset(2);
			pendingTopicDetails.setPartitionNumber(0);
			pendingTopicDetailsList.add(pendingTopicDetails);
			log.debug("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			AggregateConsumerResponse res = AggregateConsumerResponse.builder()
					.appId(entry.getKey())
					.consumed(entry.getValue().getConsumed())
					.retries(entry.getValue().getRetries())
					.deadLetter(entry.getValue().getDeadLetter())
					.pending(prodMsgCount - entry.getValue().getConsumed())
					.pendingTopicDetails(pendingTopicDetailsList)
					.build();
			agrCntResp.getConsumer().add(res);
		}
		AggregateResponse response = new AggregateResponse();
		response.setCount(agrCntResp);
		return response;
	}

	private Map<String, AggregateConsumerResponse> updateConsumerMap(List<MessageCountResponse> consumedMessageList,
																	 Map<String, AggregateConsumerResponse> consumerMap, String processingOrd) {
		for (MessageCountResponse consumedMessage : consumedMessageList) {
			String appId = consumedMessage.getConsumerAppId();
			if (consumerMap.containsKey(appId)) {
				if (PO_MAIN.equalsIgnoreCase(processingOrd)) {
					consumerMap.get(appId).setConsumed(consumerMap.get(appId).getConsumed() + consumedMessage.getMessages());
				} else if (PO_RETRY.equalsIgnoreCase(processingOrd)) {
					consumerMap.get(appId).setRetries(consumerMap.get(appId).getRetries() + consumedMessage.getMessages());
				} else if (PO_DEAD_LETTER.equalsIgnoreCase(processingOrd)) {
					consumerMap.get(appId).setDeadLetter(consumerMap.get(appId).getDeadLetter() + consumedMessage.getMessages());
				}
			}else {
				consumerMap.put(appId, AggregateConsumerResponse.builder()
						.appId(appId).consumed(consumedMessage.getMessages()).build());
			}
		}
		return consumerMap;
	}

	private int updateProducerMap(List<MessageCountResponse> producerMessageList,
								  Map<String, AggregateProducerResponse> producerMap,
								  int totalProducedMessages) {
		for (MessageCountResponse producerMessage : producerMessageList) {
			String appId = producerMessage.getConsumerAppId();
			totalProducedMessages += producerMessage.getMessages();
			if (producerMap.containsKey(appId)) {
				producerMap.get(appId).setProduced(producerMap.get(appId).getProduced() + producerMessage.getMessages());
			} else {
				producerMap.put(appId, AggregateProducerResponse.builder()
						.appId(appId).produced(producerMessage.getMessages()).build());
			}
		}
		return totalProducedMessages;
	}

	private AggregateCountResponse getAggregateCountResponse(String topicName, Long startTime, Long endTime) {

		AggregateCountResponse agrCntResp = new AggregateCountResponse();
		agrCntResp.setProducer(new ArrayList<>());
		agrCntResp.setConsumer(new ArrayList<>());
		agrCntResp.setTopicName(topicName);
		DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm a");
		Date sDate = new Date(startTime);
		Date eDate = new Date(endTime);
		agrCntResp.setStart(sdf.format(sDate));
		agrCntResp.setEnd(sdf.format(eDate));
		return agrCntResp;
	}
}