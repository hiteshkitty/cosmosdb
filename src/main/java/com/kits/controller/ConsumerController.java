package com.kits.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kits.dto.MessageCountResponse;
import com.kits.model.Consumer;
import com.kits.service.ConsumerService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/v1/consumers")
@Api(value = "Consumer APIs", tags = "Operations pertaining to performing CRUD operations for a Consumer")
public class ConsumerController {

	Logger LOGGER = LoggerFactory.getLogger(ConsumerController.class);

	@Autowired
	private ConsumerService consumerService;

	@GetMapping
	@ApiOperation(value = "Get all consumers", nickname = "getAllConsumers")
	@ApiResponses(value = { @ApiResponse(code = 500, message = "Server error"),
			@ApiResponse(code = 404, message = "Consumer not found"),
			@ApiResponse(code = 200, message = "Consumers fetchted successfully", response = ResponseEntity.class, responseContainer = "List") })
	public ResponseEntity<List<Consumer>> getAllConsumers() {
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("ContentType", "application/json");
		List<Consumer> consumerList = new ArrayList<>();

		LOGGER.info("Id is not present in the GET request");
		consumerList = consumerService.getAllConsumers();
		return new ResponseEntity<List<Consumer>>(consumerList, responseHeaders, HttpStatus.OK);

	}

	// GET all distinct topicNames
	@GetMapping("/getallmessagecount/topicname/{topicName}")
	public ResponseEntity<List<MessageCountResponse>> getAllMessageCount(@PathVariable String topicName) {
		List<MessageCountResponse> messageCountResponseList = consumerService.getAllMessageCount(topicName,
				"MAIN");
		return new ResponseEntity<List<MessageCountResponse>>(messageCountResponseList, HttpStatus.OK);
	}

}
