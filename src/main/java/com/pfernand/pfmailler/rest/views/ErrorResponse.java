package com.pfernand.pfmailler.rest.views;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import java.util.List;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Singular;
import lombok.ToString;

@Builder
@Getter
@ToString
@EqualsAndHashCode
@JsonTypeName(value = "error")
@JsonTypeInfo(
        include = JsonTypeInfo.As.WRAPPER_OBJECT,
        use = JsonTypeInfo.Id.NAME
)
public class ErrorResponse {
    private int code;
    private String message;
    @Singular private List<ErrorValue> errors;
}
