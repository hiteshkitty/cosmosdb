package com.kits.cosmos.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class PendingTopicDetails {

	private int offset;
	private int partitionNumber;
}
