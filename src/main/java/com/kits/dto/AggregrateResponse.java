package com.kits.dto;

import lombok.Data;

@Data
public class AggregrateResponse {

	private String topicName;
	private Long startTime;
	private Long endTime;

	private Integer produced = Integer.valueOf(0);
	private Integer consumed = Integer.valueOf(0);
	private Integer retries = Integer.valueOf(0);
	private Integer deadLetter = Integer.valueOf(0);
	private Integer unconsumed = Integer.valueOf(0);

	}
