package com.kits.cosmos.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AggregateProducerResponse {
	private String appId;
	private int produced;
}