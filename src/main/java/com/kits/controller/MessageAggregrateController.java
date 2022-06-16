package com.kits.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kits.dto.AggregateResponse;
import com.kits.service.AggregrateService;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api/v1/aggregrate")
@Api(value = "Producer APIs", tags = "Operations pertaining to performing CRUD operations for a Producer")
public class MessageAggregrateController {

	Logger LOGGER = LoggerFactory.getLogger(MessageAggregrateController.class);

	@Autowired
	private AggregrateService aggregrateService;

	@GetMapping("/getmissedmessages/topicname/{topicName}/starttime/{startTime}/endtime/{endTime}")
	public ResponseEntity<AggregateResponse> getAllMissedMessageCountsWithTime(@PathVariable String topicName,
			@PathVariable String startTime, @PathVariable String endTime) {
		AggregateResponse response = aggregrateService.getMissedMessagesWithLatestTime(topicName,
				Long.valueOf(startTime), Long.valueOf(endTime));
		return new ResponseEntity<AggregateResponse>(response, HttpStatus.OK);
	}

}
