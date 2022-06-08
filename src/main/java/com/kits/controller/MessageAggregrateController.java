package com.kits.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kits.dto.AggregrateResponse;
import com.kits.service.AggregrateService;
import com.kits.service.TimeEnum;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api/v1/aggregrate")
@Api(value = "Producer APIs", tags = "Operations pertaining to performing CRUD operations for a Producer")
public class MessageAggregrateController {

	Logger LOGGER = LoggerFactory.getLogger(MessageAggregrateController.class);

	@Autowired
	private AggregrateService aggregrateService;

	@GetMapping("/getmissedmessages/topicname/{topicName}")
	public ResponseEntity<AggregrateResponse> getAllMissedMessageCount(@PathVariable String topicName) {
		AggregrateResponse response = aggregrateService.getAllMissedMessageCount(topicName);
		return new ResponseEntity<AggregrateResponse>(response, HttpStatus.OK);
	}
	
	@GetMapping("/getmissedmessages/topicname/{topicName}/time/{time}")
	public ResponseEntity<AggregrateResponse> getAllMissedMessageCounts(@PathVariable String topicName, @PathVariable TimeEnum time) {
		AggregrateResponse response = aggregrateService.getAllMissedMessageCountByTime(topicName,time);
		return new ResponseEntity<AggregrateResponse>(response, HttpStatus.OK);
	}

}
