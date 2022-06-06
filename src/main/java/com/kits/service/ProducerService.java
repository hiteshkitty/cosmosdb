package com.kits.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.azure.cosmos.models.PartitionKey;
import com.kits.dto.MessageCountResponse;
import com.kits.model.Producer;
import com.kits.repository.ProducerDBRepository;

@Component
public class ProducerService {

	Logger LOGGER = LoggerFactory.getLogger(ProducerService.class);
	
	@Autowired
	ProducerDBRepository producerDBRepository;

	private List<Producer> producerList = new ArrayList<>();


	public Producer save(Producer producer) {
		producerDBRepository.save(producer);
		return producer;
	}

	public Producer findById(String id) {
		Producer prod = producerDBRepository.findByCorrelationId(id);
		return prod;
	}

	public List<Producer> getAllProducers() {
		LOGGER.info("fetching all producers");
		return producerDBRepository.getAllProducers();
	}

	public void deleteById(String id, PartitionKey partitionKey) {
		Producer prod = producerList.stream().filter(producer -> id.equals(producer.getCorrelationId())).findAny()
				.orElse(null);
		producerList.remove(prod);

	}

	public Producer findByTopicName(String topicName) {
		// TODO Auto-generated method stub
		return producerDBRepository.findByTopicName(topicName);
	}

	public Long findCountByTopicName(String topicName) {
		// TODO Auto-generated method stub
		return producerDBRepository.countByTopicName(topicName);
	}

	public Producer findByCorrelationIdAndTopicName(String correlationId, String topicName) {
		return producerDBRepository.findByCorrelationIdAndTopicName(correlationId, topicName);
	}

	public List<Producer> getProducersWithOffsetLimit(int offset, int limit) {
		return producerDBRepository.getProducersWithOffsetLimit(offset, limit);
	}

	public long getNumberOfProducersWithTopicName(String topicName) {
		LOGGER.debug("fetching number of prodcuers with topicName: " + topicName);
		return producerDBRepository.getNumberOfProducersWithTopicName(topicName);
	}

	/**
	 * 
	 * @return
	 */
	public List<String> findAllDistinctTopicNames() {
		return producerDBRepository.findAllDistinctTopicNames();
	}

	public long getProdcuerCountForDuration(TimeEnum time) {
		LOGGER.debug("fetching number of prodcuers for a duration: " + time.getValue());
		int count = 0;
		long timeValue = time.getValue();
		System.out.println("timeValue : " + timeValue);
//		timeValue = System.currentTimeMillis() - timeValue;
//		System.out.println("new time: " + timeValue + " date: " + new Date(timeValue));
		count =  producerDBRepository.getProdcuerCountForDuration(timeValue);
		
		return count;
	}

	public List<MessageCountResponse> getAllMessageCount(String topicName, String processorType) {
		LOGGER.debug("fetching all message count for ${topicName} and ${processorType} " + topicName, processorType);
		List<MessageCountResponse> list = new ArrayList<>();
		list = producerDBRepository.getAllMessageCount(topicName, processorType);
		return list;
	}
	
}
