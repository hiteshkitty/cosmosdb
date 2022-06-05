package com.kits.model;

import java.util.Date;

import org.springframework.data.annotation.Id;

import com.azure.spring.data.cosmos.core.mapping.Container;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Container(containerName = "myproducers", ru = "400", autoCreateContainer=false)
@ApiModel
public class Producer {

	@Id
	@ApiModelProperty(position = 1, required = true, value = "A00001")
	private String correlationId;
	private String consumerAppId;
	@ApiModelProperty(position = 3, required = true, value = "test-topic")
	private String topicName;
	private Integer partitionNumber;
	private Integer ofset;
	private String processorType;
//	@JsonFormat
//    (shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm")
	private Long timeStamp;

	public Producer() {
		// TODO Auto-generated constructor stub
	}

	
	public Producer(String correlationId, String consumerAppId, String topicName, Integer partitionNumber,
			Integer ofset, String processorType, Long timeStamp) {
		super();
		this.correlationId = correlationId;
		this.consumerAppId = consumerAppId;
		this.topicName = topicName;
		this.partitionNumber = partitionNumber;
		this.ofset = ofset;
		this.processorType = processorType;
		this.timeStamp = timeStamp;
	}


	/**
	 * @return the correlationId
	 */
	public String getCorrelationId() {
		return correlationId;
	}

	/**
	 * @param correlationId the correlationId to set
	 */
	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}

	/**
	 * @return the consumerAppId
	 */
	public String getConsumerAppId() {
		return consumerAppId;
	}

	/**
	 * @param consumerAppId the consumerAppId to set
	 */
	public void setConsumerAppId(String consumerAppId) {
		this.consumerAppId = consumerAppId;
	}

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
	 * @return the ofset
	 */
	public Integer getOset() {
		return ofset;
	}

	/**
	 * @param ofset the ofset to set
	 */
	public void setOfset(Integer ofset) {
		this.ofset = ofset;
	}

	/**
	 * @return the processorType
	 */
	public String getProcessorType() {
		return processorType;
	}

	/**
	 * @param processorType the processorType to set
	 */
	public void setProcessorType(String processorType) {
		this.processorType = processorType;
	}

	/**
	 * @return the timeStamp
	 */
	public Long getTimeStamp() {
		return timeStamp;
	}

	/**
	 * @param timeStamp the timeStamp to set
	 */
	public void setTimeStamp(Long timeStamp) {
		this.timeStamp = timeStamp;
	}

	@Override
	public String toString() {
		return "Producer [correlationId=" + correlationId + ", consumerAppId=" + consumerAppId + ", topicName="
				+ topicName + ", partitionNumber=" + partitionNumber + ", ofset=" + ofset + ", processorType="
				+ processorType + ", timeStamp=" + timeStamp + "]";
	}

}
