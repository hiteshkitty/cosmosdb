package com.kits.dto;


import lombok.Data;

@Data
public class MessageCountResponse {
    private int partitionNumber;
    private int minOffset;
    private int maxOffset;
    private int messages;
    private String consumerAppId;
}
