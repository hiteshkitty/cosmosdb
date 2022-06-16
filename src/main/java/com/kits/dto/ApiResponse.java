package com.kits.dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ApiResponse implements Serializable {

	@JsonProperty("count")
	private CountResponse countResponse;
	@JsonProperty("consumer")
	private List<ConsumerData> consumer;

}
