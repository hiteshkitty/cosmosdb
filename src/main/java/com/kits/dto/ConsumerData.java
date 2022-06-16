package com.kits.dto;

import lombok.Data;

@Data
public class ConsumerData {
	private String consumerAppId;
	private int consumed;
	private int retires;
	private int deadLetter;
	private int pending;

}
