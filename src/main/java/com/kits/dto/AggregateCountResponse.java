package com.kits.dto;

import lombok.Data;

@Data
public class AggregateCountResponse{
    private String topicName;
    private Long startTime;
    private Long endTime;
    private AggregateProducerResponse producer;
}