package com.pfernand.pfmailler.domain.validation;

import com.pfernand.pfmailler.domain.exceptions.InvalidEmailException;
import com.pfernand.pfmailler.model.Email;
import com.pfernand.pfmailler.model.EmailValidation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import javax.inject.Inject;
import javax.inject.Named;

@Slf4j
@Named("emailValidatorRealEmail")
@Deprecated
public class EmailValidatorRealEmail implements EmailValidator {

    private static final String REAL_EMAIL_URL = "https://realemail.expeditedaddons.com";

    private final RestTemplate restTemplate;

    private final String realEmailApiKey;

    @Inject
    public EmailValidatorRealEmail(RestTemplate restTemplate, @Value("${realemail.api.key}") String realEmailApiKey) {
        this.restTemplate = restTemplate;
        this.realEmailApiKey = realEmailApiKey;
    }

    @Override
    public void isValidEmail(Email email) throws InvalidEmailException {

        log.info("Getting validation for email: {}", email.getTo());
        ResponseEntity<EmailValidation> responseEntity = restTemplate.getForEntity(
                String.format("%s/?api_key=%s&email=%s&fix_typos=false",
                        REAL_EMAIL_URL,
                        realEmailApiKey,
                        email.getTo()
                ), EmailValidation.class
        );
        if (!responseEntity.getBody().isMxRecordsFound()) {
            log.error("Invalid email: {}", email.getTo());
            throw new InvalidEmailException(email.getTo());
        }
    }
}
