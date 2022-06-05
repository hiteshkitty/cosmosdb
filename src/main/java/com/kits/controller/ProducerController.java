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
import com.kits.service.TimeEnum;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/v1/producers")
@Api(value = "Producer APIs", tags = "Operations pertaining to performing CRUD operations for a Producer")
public class ProducerController {

	Logger LOGGER = LoggerFactory.getLogger(ProducerController.class);

	@Autowired
	private ProducerService producerService;

	// Add new Producer
	@ApiOperation(value = "Create a Producer", nickname = "createProducer")
	@ApiResponses(value = { @ApiResponse(code = 500, message = "Server error"),
			@ApiResponse(code = 404, message = "Producer not found"),
			@ApiResponse(code = 201, message = "Producer created successfully", response = ResponseEntity.class, responseContainer = "ResponseEntity") })
	@PostMapping
	public ResponseEntity<ProducerResponse> createNewProducer(
			@ApiParam(value = "Producer", required = true, defaultValue = "null") @RequestBody Producer producer) {

		producer = producerService.save(producer);
		ProducerResponse producerResponse = new ProducerResponse();
		producerResponse.setMessage("New producer created successfully with the ID: " + producer.getCorrelationId());
		producerResponse.setStatusCode("200");
		return new ResponseEntity<ProducerResponse>(producerResponse, HttpStatus.CREATED);
	}

	// Update existing Producer
	@PutMapping("/{id}")
	public ResponseEntity<String> updateExistingProducer(@PathVariable String id, @RequestBody Producer producer) {

		producerService.save(producer);
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
	@ApiOperation(value = "Get all producers", nickname = "getAllProducers")
	@ApiResponses(value = { @ApiResponse(code = 500, message = "Server error"),
			@ApiResponse(code = 404, message = "Producer not found"),
			@ApiResponse(code = 200, message = "Producers fetchted successfully", response = ResponseEntity.class, responseContainer = "List") })
	public ResponseEntity<List<Producer>> getAllProducers() {
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("ContentType", "application/json");
		List<Producer> producerList = new ArrayList<>();

		LOGGER.info("Id is not present in the GET request");
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

	// GET the producer of a particular topicName
	@GetMapping("/topicname/{topicName}")
	public ResponseEntity<Producer> findByTopicName(@PathVariable String topicName) {
		Producer producer = producerService.findByTopicName(topicName);
		return new ResponseEntity<Producer>(producer, HttpStatus.OK);
	}

	// GET the count of Producers for a topicname
	@GetMapping("/countbytopicname/{topicName}")
	public ResponseEntity<Long> findCountByTopicName(@PathVariable String topicName) {
		Long count = producerService.findCountByTopicName(topicName);
		return new ResponseEntity<Long>(count, HttpStatus.OK);
	}

	// GET the producer of a particular correlationId and topicName
	@GetMapping("/correlationid/{correlationId}/topicname/{topicName}")
	public ResponseEntity<Producer> findByCorrelationIdAndTopicName(@PathVariable String correlationId,
			@PathVariable String topicName) {
		Producer producer = producerService.findByCorrelationIdAndTopicName(correlationId, topicName);
		return new ResponseEntity<Producer>(producer, HttpStatus.OK);
	}

	// GET the producer of a particular correlationId and topicName
	@GetMapping("/offset/{offset}/limit/{limit}")
	public ResponseEntity<List<Producer>> getProducersWithOffsetLimit(@PathVariable int offset,
			@PathVariable int limit) {
		List<Producer> producerList = producerService.getProducersWithOffsetLimit(offset, limit);
		return new ResponseEntity<List<Producer>>(producerList, HttpStatus.OK);
	}

	// GET the producer of a particular correlationId and topicName
	@GetMapping("/numberofproducers/{topicName}")
	public ResponseEntity<Long> getNumberOfProducersWithTopicName(@PathVariable String topicName) {
		long count = producerService.getNumberOfProducersWithTopicName(topicName);
		return new ResponseEntity<Long>(Long.valueOf(count), HttpStatus.OK);
	}

	@GetMapping("/countbytime/{time}")
	public ResponseEntity<Long> getProdcuerCountForDuration(@PathVariable TimeEnum time) {
		long count = producerService.getProdcuerCountForDuration(time);
		return new ResponseEntity<Long>(Long.valueOf(count), HttpStatus.OK);
	}

	// GET all distinct topicNames
	@GetMapping("/getalltopicnames")
	public ResponseEntity<List<String>> getAllTopicNames() {
		List<String> topicNameList = producerService.findAllDistinctTopicNames();
		return new ResponseEntity<List<String>>(topicNameList, HttpStatus.OK);
	}
	
	

}
