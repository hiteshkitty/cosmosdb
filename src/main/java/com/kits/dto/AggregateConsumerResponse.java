package com.kits.dto;

import lombok.Data;

@Data
public class AggregateConsumerResponse{
    private String appId;
    private int consumed;
    private int retries;
    private int deadLetter;
    private int pending;
}