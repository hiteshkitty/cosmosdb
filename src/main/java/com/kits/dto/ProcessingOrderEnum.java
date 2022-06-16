package com.kits.dto;

public enum ProcessingOrderEnum {

	MAIN("MAIN"), RETRY("retry"), DEAD_LETTER("dead-letter");

	private String value;

	private ProcessingOrderEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
