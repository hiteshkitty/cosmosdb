package com.kits.dto;

import lombok.Data;

@Data
public class CountResponse {

	private String topicName;
	private long startTime;
	private long endTime;
	private ProducerData producer;

}
