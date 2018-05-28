package com.pfernand.pfmailler.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown=true)
@Deprecated
public class EmailValidation {
    private boolean valid;
    @JsonProperty("is_free_email")
    private boolean isFreeEmail;
    private String provider;
    @JsonProperty("domain_error")
    private boolean domainError;
    private String domain;
    @JsonProperty("syntax_error")
    private boolean syntaxError;
    @JsonProperty("is_disposable")
    private boolean isDisposable;
    private String email;
    @JsonProperty("is_personal")
    private boolean isPersonal;
    @JsonProperty("mx_records_found")
    private boolean mxRecordsFound;
}
