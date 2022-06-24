package com.kits.cosmos.dto;

import lombok.Data;

@Data
public class TopicNameResponse {
	private String topicName;

	public TopicNameResponse(String topicName) {
		this.topicName = topicName;
	}
}