package com.kits.cosmos.dto;

import lombok.Data;

@Data
public class MessageProcessed {
	private int offset;
	private int partitionNumber;
}
