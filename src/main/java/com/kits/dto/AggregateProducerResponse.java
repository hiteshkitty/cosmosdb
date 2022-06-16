package com.kits.dto;

import lombok.Data;

@Data
public class AggregateProducerResponse{
    private String appId;
    private int produced;
}