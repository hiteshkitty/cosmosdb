package com.kits.cosmos.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class DateRangeRequest implements Serializable {

	private String startDate;
	private String startTime;
	private String endDate;
	private String endTime;
}
