package com.pfernand.pfmailler.rest.views;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.JsonNode;
import java.time.Clock;
import java.time.Instant;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
@Builder
@JsonTypeName(value = "maillerResponse")
@JsonTypeInfo(
        include = JsonTypeInfo.As.WRAPPER_OBJECT,
        use = JsonTypeInfo.Id.NAME
)
public class MaillerResponse {
    private final long currentTime;
    private final JsonNode inputParameters;
}
