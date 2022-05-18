package com.kits.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.azure.cosmos.models.PartitionKey;
import com.kits.model.Producer;
import com.kits.repository.ProducerDBRepository;

@Component
public class ProducerService {

	@Autowired
	ProducerDBRepository producerDBRepository;

	private List<Producer> producerList = new ArrayList<>();

	public ProducerService() {
		Producer p1 = new Producer("A001", "AppId001", "test-topic1", 3, 45, "general", new Date());
		Producer p2 = new Producer("A002", "AppId002", "test-topic2", 3, 45, "general", new Date());
		Producer p3 = new Producer("A003", "AppId003", "test-topic3", 3, 45, "general", new Date());
		Producer p4 = new Producer("A004", "AppId004", "test-topic4", 3, 45, "general", new Date());
		Producer p5 = new Producer("A005", "AppId005", "test-topic5", 3, 45, "general", new Date());

		producerList.add(p1);
		producerList.add(p2);
		producerList.add(p3);
		producerList.add(p4);
		producerList.add(p5);

	}

	public Producer save(Producer c) {
		producerList.add(c);
		return c;
	}

	public Producer findById(String id) {
		Producer prod = producerList.stream().filter(producer -> id.equals(producer.getCorrelationId())).findAny()
				.orElse(null);
		System.out.println(prod);
		return prod;
	}

	public List<Producer> getAllProducers() {
		return producerDBRepository.getAllProducers();
	}

	public void deleteById(String id, PartitionKey partitionKey) {
		Producer prod = producerList.stream().filter(producer -> id.equals(producer.getCorrelationId())).findAny()
				.orElse(null);
		producerList.remove(prod);
		
	}

}
