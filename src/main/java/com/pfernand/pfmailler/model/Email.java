package com.pfernand.pfmailler.model;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Builder
@JsonTypeName(value = "emailParameters")
@JsonTypeInfo(
        include = JsonTypeInfo.As.WRAPPER_OBJECT,
        use = JsonTypeInfo.Id.NAME
)
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Email {
    private String from;
    private String to;
    private String subject;
    private String body;
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime sentTime;
    @Builder.Default
    private boolean valid = false;
}
