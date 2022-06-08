package com.kits.dto;

public class AggregrateResponse {

	private String topicName;
	private Long startTime;
	private Long endTime;

	private Integer produced;
	private Integer consumed;
	private Integer retries;
	private Integer deadLetter;
	private Integer unconsumed;

	/**
	 * @return the topicName
	 */
	public String getTopicName() {
		return topicName;
	}

	/**
	 * @param topicName the topicName to set
	 */
	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}

	/**
	 * @return the startTime
	 */
	public Long getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the endTime
	 */
	public Long getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}

	/**
	 * @return the produced
	 */
	public Integer getProduced() {
		return produced;
	}

	/**
	 * @param produced the produced to set
	 */
	public void setProduced(Integer produced) {
		this.produced = produced;
	}

	/**
	 * @return the consumed
	 */
	public Integer getConsumed() {
		return consumed;
	}

	/**
	 * @param consumed the consumed to set
	 */
	public void setConsumed(Integer consumed) {
		this.consumed = consumed;
	}

	/**
	 * @return the retries
	 */
	public Integer getRetries() {
		return retries;
	}

	/**
	 * @param retries the retries to set
	 */
	public void setRetries(Integer retries) {
		this.retries = retries;
	}

	/**
	 * @return the deadLetter
	 */
	public Integer getDeadLetter() {
		return deadLetter;
	}

	/**
	 * @param deadLetter the deadLetter to set
	 */
	public void setDeadLetter(Integer deadLetter) {
		this.deadLetter = deadLetter;
	}

	/**
	 * @return the unconsumed
	 */
	public Integer getUnconsumed() {
		return unconsumed;
	}

	/**
	 * @param unconsumed the unconsumed to set
	 */
	public void setUnconsumed(Integer unconsumed) {
		this.unconsumed = unconsumed;
	}

	@Override
	public String toString() {
		return "AggregrateResponse [topicName=" + topicName + ", startTime=" + startTime + ", endTime=" + endTime
				+ ", produced=" + produced + ", consumed=" + consumed + ", retries=" + retries + ", deadLetter="
				+ deadLetter + ", unconsumed=" + unconsumed + "]";
	}
}
