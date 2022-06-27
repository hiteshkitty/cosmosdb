package com.kits.cosmos.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class PendingTopicDetails {

	private int offset;
	private int partitionNumber;
	private String topicName;
	private String consumerAppId;
	private String processingOrder;
}
