package com.kits.dto;

import lombok.Data;

import java.util.List;

@Data
public class AggregateResponse {
    private AggregateCountResponse count;
    private List<AggregateConsumerResponse> consumer;
    private int pendingMessages;
}





