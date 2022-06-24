package com.kits.cosmos.dto;

import java.util.List;

import lombok.Data;

@Data
public class AggregateCountResponse {
	private String topicName;
	private String start;
	private String end;
	private List<AggregateProducerResponse> producer;
	private List<AggregateConsumerResponse> consumer;
}