package com.kits.dto;

public class AggregrateResponse {

	private String topicName;
	private String processingOrder;
	private Integer partitionName;
	private Integer missedMessages;

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
	 * @return the missedMessages
	 */
	public Integer getMissedMessages() {
		return missedMessages;
	}

	/**
	 * @param missedMessages the missedMessages to set
	 */
	public void setMissedMessages(Integer missedMessages) {
		this.missedMessages = missedMessages;
	}

	/**
	 * @return the processingOrder
	 */
	public String getProcessingOrder() {
		return processingOrder;
	}

	/**
	 * @param processingOrder the processingOrder to set
	 */
	public void setProcessingOrder(String processingOrder) {
		this.processingOrder = processingOrder;
	}

	/**
	 * @return the partitionName
	 */
	public Integer getPartitionName() {
		return partitionName;
	}

	/**
	 * @param partitionName the partitionName to set
	 */
	public void setPartitionName(Integer partitionName) {
		this.partitionName = partitionName;
	}

	@Override
	public String toString() {
		return "AggregrateResponse [topicName=" + topicName + ", processingOrder=" + processingOrder
				+ ", partitionName=" + partitionName + ", missedMessages=" + missedMessages + "]";
	}

}
