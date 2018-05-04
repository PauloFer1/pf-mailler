package com.pfernand.pfmailler.domain.validation;

import com.pfernand.pfmailler.domain.exceptions.InvalidEmailException;
import com.pfernand.pfmailler.model.Email;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Named;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

@Slf4j
@Named("emailValidatorJavaMail")
public class EmailValidatorJavaMail implements EmailValidator {
    @Override
    public void isValidEmail(Email email) throws InvalidEmailException {
        log.info("Validating email: {}", email.getTo());
        try {
            InternetAddress internetAddress = new InternetAddress(email.getTo());
            internetAddress.validate();
        } catch (AddressException e) {
            log.error("Invalid email. cause: {}", e.getMessage());
            throw new InvalidEmailException(email.getTo());
        }
    }
}
