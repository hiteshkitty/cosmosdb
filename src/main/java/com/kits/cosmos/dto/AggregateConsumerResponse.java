package com.kits.cosmos.dto;

import java.util.ArrayList;
import java.util.List;

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
	private List<PendingTopicDetails> pendingTopicDetails = new ArrayList<>();
}