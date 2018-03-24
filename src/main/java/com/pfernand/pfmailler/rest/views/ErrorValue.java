package com.pfernand.pfmailler.rest.views;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
@EqualsAndHashCode
public class ErrorValue {
    private String domain;
    private String reason;
    private String target;
}
