package com.kits.service;

public enum TimeEnum {

	MINS_15(1000 * 60 * 15), MINS_5(1000 * 60 * 5);

	private int value;

	private TimeEnum(int value) {
		this.value = value;
	}

	/**
	 * @return the value
	 */
	public int getValue() {
		return value;
	}

}
