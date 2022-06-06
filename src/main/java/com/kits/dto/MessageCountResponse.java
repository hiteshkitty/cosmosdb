package com.kits.dto;

import java.io.Serializable;

/**
 * 
 * @author hitessha
 *
 */
public class MessageCountResponse implements Serializable {

	private Integer partitionNumber;
	private Integer minOffset;
	private Integer maxOffset;
	private Integer messages;

	/**
	 * @return the partitionNumber
	 */
	public Integer getPartitionNumber() {
		return partitionNumber;
	}

	/**
	 * @param partitionNumber the partitionNumber to set
	 */
	public void setPartitionNumber(Integer partitionNumber) {
		this.partitionNumber = partitionNumber;
	}

	/**
	 * @return the minOffset
	 */
	public Integer getMinOffset() {
		return minOffset;
	}

	/**
	 * @param minOffset the minOffset to set
	 */
	public void setMinOffset(Integer minOffset) {
		this.minOffset = minOffset;
	}

	/**
	 * @return the maxOffset
	 */
	public Integer getMaxOffset() {
		return maxOffset;
	}

	/**
	 * @param maxOffset the maxOffset to set
	 */
	public void setMaxOffset(Integer maxOffset) {
		this.maxOffset = maxOffset;
	}

	/**
	 * @return the messages
	 */
	public Integer getMessages() {
		return messages;
	}

	/**
	 * @param messages the messages to set
	 */
	public void setMessages(Integer messages) {
		this.messages = messages;
	}

	@Override
	public String toString() {
		return "MessageCountResponse [partitionNumber=" + partitionNumber + ", minOffset=" + minOffset + ", maxOffset="
				+ maxOffset + ", messages=" + messages + "]";
	}

}
