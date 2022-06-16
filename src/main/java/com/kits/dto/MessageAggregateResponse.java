package com.kits.dto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;


@Getter
@Setter
@NoArgsConstructor
public class MessageAggregateResponse implements Serializable {
    private String topicName;
    private Long startTime;
    private Long endTime;
    private Integer produced;
    private Integer consumed;
    private Integer retries;
    private Integer deadLetter;
    private Integer notConsumed;
}
