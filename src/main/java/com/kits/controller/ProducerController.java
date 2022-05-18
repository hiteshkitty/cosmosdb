package com.kits.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.azure.cosmos.models.PartitionKey;
import com.kits.dto.ProducerResponse;
import com.kits.model.Producer;
import com.kits.service.ProducerService;

@RestController
@RequestMapping("/api/v1/producers")
public class ProducerController {

	@Autowired
	private ProducerService producerService;

	Logger logger = LoggerFactory.getLogger(ProducerController.class);

	// Add new Producer
	@PostMapping
	public ResponseEntity<ProducerResponse> createNewProducer(@RequestBody Producer c) {

		c = producerService.save(c);
		ProducerResponse producerResponse = new ProducerResponse();
		producerResponse.setMessage("New producer created successfully with the ID: " + c.getCorrelationId());
		producerResponse.setStatusCode("00");
		return new ResponseEntity<ProducerResponse>(producerResponse, HttpStatus.CREATED);
	}

	// Update existing Producer
	@PutMapping("/{id}")
	public ResponseEntity<String> updateExistingProducer(@PathVariable String id, @RequestBody Producer c) {

		producerService.save(c);
		return new ResponseEntity<String>("", HttpStatus.NO_CONTENT);
	}

	// get producer details
	@GetMapping("/{id}")
	public ResponseEntity<Producer> getProducers(@PathVariable String id) {
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("ContentType", "application/json");

		Producer producer = producerService.findById(id);

		return new ResponseEntity<Producer>(producer, responseHeaders, HttpStatus.NOT_FOUND);

	}

	@GetMapping
	public ResponseEntity<List<Producer>> getAllProducers() {
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("ContentType", "application/json");
		List<Producer> producerList = new ArrayList<>();

		logger.info("Id is not present in the GET request");
		producerList = producerService.getAllProducers();
		return new ResponseEntity<List<Producer>>(producerList, responseHeaders, HttpStatus.OK);

	}

	// delete the producer of a particular id
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteExistingProducer(@PathVariable String id) {
		Producer producer = producerService.findById(id);
		producerService.deleteById(id, new PartitionKey(producer.getCorrelationId()));
		return new ResponseEntity<String>("", HttpStatus.NO_CONTENT);
	}
}
