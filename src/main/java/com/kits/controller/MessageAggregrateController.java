package com.kits.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kits.cosmos.dto.AggregateResponse;
import com.kits.cosmos.dto.DateRangeRequest;
import com.kits.cosmos.dto.ProducerResponse;
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

	@PostMapping("/getmissedmessages/topicname/{topicName}")
	public ResponseEntity<AggregateResponse> getAllMissedMessageCountsWithTime(@PathVariable String topicName,
			@RequestBody DateRangeRequest request) throws ParseException {
		try {
			validateDateRangeRequest(request);
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}

		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		Long startTime = sdf.parse(request.getStartDate() + " " + request.getStartTime()).getTime();
		Long endTime = sdf.parse(request.getEndDate() + " " + request.getEndTime()).getTime();

		AggregateResponse response = aggregrateService.getMissedMessagesWithLatestTime(topicName, startTime, endTime);
		return new ResponseEntity<AggregateResponse>(response, HttpStatus.OK);
	}

	@GetMapping("getalltopics")
	public ResponseEntity<ProducerResponse> getProducers() {
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("ContentType", "application/json");

		ProducerResponse producerResponse = aggregrateService.getAllTopics();

		return new ResponseEntity<ProducerResponse>(producerResponse, responseHeaders, HttpStatus.OK);

	}
	
	@PostMapping("/getpendingmessages/topicname/{topicName}")
	public ResponseEntity<Map<Integer, List<Integer>> > getAllPendingMessages(@PathVariable String topicName,
			@RequestBody DateRangeRequest request) throws ParseException {
		try {
			validateDateRangeRequest(request);
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}

		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		Long startTime = sdf.parse(request.getStartDate() + " " + request.getStartTime()).getTime();
		Long endTime = sdf.parse(request.getEndDate() + " " + request.getEndTime()).getTime();

		Map<Integer, List<Integer>>  response = aggregrateService.getAllPendingMessages(topicName, startTime, endTime);
		return new ResponseEntity<Map<Integer, List<Integer>> >(response, HttpStatus.OK);
	}
	private void validateDateRangeRequest(DateRangeRequest request) throws Exception {

		try {
			if (StringUtils.isEmpty(request.getStartDate()) || StringUtils.isEmpty(request.getEndDate())
					|| StringUtils.isEmpty(request.getStartTime()) || StringUtils.isEmpty(request.getStartTime())) {
				throw new Exception("Bad request, one of the mendatory field is missing");
			}

			checkDateFormat(request);

			SimpleDateFormat sdf = new SimpleDateFormat("mm-dd-yyyy hh:mm");
			Date startDate = sdf.parse(request.getStartDate() + " " + request.getStartTime());
			Date endDate = sdf.parse(request.getEndDate() + " " + request.getEndTime());

			if (startDate.compareTo(endDate) > 0) {
				LOGGER.error("startDate is after endDate");
				throw new Exception("Start date can't be after endDate");
			}
		} catch (Exception ex) {
			if (ex instanceof ParseException) {
				ex = new Exception("Bad request, please check dates are in mm-dd-yyyy format and time in hh:mm");
			}
			throw ex;
		}
	}

	private void checkFormat(String input, String regex) throws Exception {
		boolean flag = false;
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(input);
		flag = matcher.matches();

		if (!flag) {
			throw new Exception("Bad request, please check dates are in dd-MMM-yyyy format and time in HH:mm:ss");
		}
	}
	

	private boolean checkDateFormat(DateRangeRequest request) throws Exception {
		boolean flag = true;
//		String dateRegex = "\\d{1,2}/d{1,2}/\\d{4}";
		String dateRegex = "^(3[01]|[12][0-9]|0[1-9])-(1[0-2]|0[1-9])-[0-9]{4}$";
//		String timeRegex = "^(?:[01]\\d|2[0123]):(?:[012345]\\d) ([AaPp][Mm])*";
		String timeRegex = "^([0-1][0-9]:[0-5][0-9])\\s*([AaPp][Mm])*";

		checkFormat(request.getStartDate(), dateRegex);
		checkFormat(request.getEndDate(), dateRegex);
		checkFormat(request.getStartTime(), timeRegex);
		checkFormat(request.getEndTime(), timeRegex);

		return flag;
	}
	
	private boolean checkDateFormat1(DateRangeRequest request) throws Exception {
		boolean flag = true;
//		String dateRegex = "\\d{1,2}/d{1,2}/\\d{4}";
		String dateRegex = "^([12][0-9][0-9][0-9]-[0-2][0-9]-[0-2][0-9])";
//		String timeRegex = "^(?:[01]\\d|2[0123]):(?:[012345]\\d) ([AaPp][Mm])*";
		String timeRegex = "^([0-2][0-9]:[0-5][0-9])";

		checkFormat(request.getStartDate(), dateRegex);
		checkFormat(request.getEndDate(), dateRegex);
		checkFormat(request.getStartTime(), timeRegex);
		checkFormat(request.getEndTime(), timeRegex);

		return flag;
	}
	
	public static void main(String[] args) throws Exception {
		MessageAggregrateController con = new MessageAggregrateController();
		DateRangeRequest request = new DateRangeRequest();
		request.setStartDate("11-12-1970");
		request.setStartTime("12:12 AM");
		request.setEndDate("12-12-1971");
		request.setEndTime("03:15 PM");
//		con.validateDateRangeRequest(request);
		System.out.println("fff");

		request.setStartDate("1970-12-11");
		request.setStartTime("12:12");
		request.setEndDate("1970-12-12");
		request.setEndTime("03:15");
		con.checkDateFormat1(request);
		System.out.println("hhh");
	}
}
