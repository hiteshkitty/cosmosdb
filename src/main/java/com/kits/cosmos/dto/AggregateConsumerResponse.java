package com.kits.cosmos.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AggregateConsumerResponse {
	private String appId;
	private int consumed;
	private int retries;
	private int deadLetter;
	private int pending;
}