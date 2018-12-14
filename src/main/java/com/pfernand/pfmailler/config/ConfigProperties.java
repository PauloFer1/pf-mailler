package com.pfernand.pfmailler.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Getter
@Component
@Named
public class ConfigProperties {

    private final String authorisationKey;
    private final String authorisationSecret;
    private final List<String> allowedOrigins;

    @Inject
    public ConfigProperties(
            @Value("${security.authorisation.key}") String authorisationKey,
            @Value("${security.authorisation.secret}") String authorisationSecret,
            @Value("#{'${cors.origin.allowed}'.split(',')}") List<String> allowedOrigins
    ) {
        this.authorisationKey = authorisationKey;
        this.authorisationSecret = authorisationSecret;
        this.allowedOrigins = allowedOrigins;
    }
}
