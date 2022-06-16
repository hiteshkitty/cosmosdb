package com.kits.dto;

import java.io.Serializable;

import lombok.Data;

/**
 * 
 * @author hitessha
 *
 */
@Data
public class MessageCountResponse implements Serializable {

	private String consumerAppId;
	private Integer partitionNumber;
	private Integer minOffset;
	private Integer maxOffset;
	private Integer messages;

}
