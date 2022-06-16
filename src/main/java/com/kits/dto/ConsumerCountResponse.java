package com.kits.dto;

import lombok.Data;

@Data 
public class ConsumerCountResponse {

	private String consumerAppId;
	private int messages;
	
}
