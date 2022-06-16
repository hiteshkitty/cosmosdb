package com.kits.model;

import org.springframework.data.annotation.Id;

import com.azure.spring.data.cosmos.core.mapping.Container;
import com.kits.dto.MetaDataTypeEnum;
import com.kits.dto.ProcessingOrderEnum;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Container(containerName = "myconsumers", ru = "400", autoCreateContainer = true)
@ApiModel
public class Consumer {

	@Id
	private String correlationId;
	private String topicName;
	private Integer partitionNumber;
	private Long offset;
	private Long timestamp;
	private MetaDataTypeEnum metadataType;
	private ProcessingOrderEnum processingOrder;
	private String appId;
	private Long processTimestamp;

}
