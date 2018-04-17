package com.pfernand.pfmailler.rest.views;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.pfernand.pfmailler.model.Email;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.Singular;

@Data
@Builder
@JsonTypeName(value = "maillerResponse")
@JsonTypeInfo(
    include = JsonTypeInfo.As.WRAPPER_OBJECT,
    use = JsonTypeInfo.Id.NAME
)
public class EmailListResponse {
    private final long currentTime;
    @Singular
    private List<Email> emails;
}
