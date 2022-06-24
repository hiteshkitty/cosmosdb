package com.kits.model;

import org.springframework.data.annotation.Id;

import com.azure.spring.data.cosmos.core.mapping.Container;
import com.kits.cosmos.dto.MetaDataTypeEnum;
import com.kits.cosmos.dto.ProcessingOrderEnum;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@Container(containerName = "myproducers", ru = "400", autoCreateContainer = false)
@ApiModel
public class Producer {

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
