package com.kits.cosmos.dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ProducerResponse implements Serializable {

	@JsonProperty("topics")
	private List<TopicNameResponse> topics;
}
