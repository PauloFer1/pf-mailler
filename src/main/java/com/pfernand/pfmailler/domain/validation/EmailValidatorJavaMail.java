package com.pfernand.pfmailler.domain.validation;

import com.pfernand.pfmailler.domain.exceptions.InvalidEmailException;
import com.pfernand.pfmailler.model.Email;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;

import javax.inject.Named;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

@Slf4j
@RequiredArgsConstructor
@Named("emailValidatorJavaMail")
@Primary
public class EmailValidatorJavaMail implements EmailValidator {

    private final InternetAddress internetAddress;

    @Override
    public void isValidEmail(final Email email) throws InvalidEmailException {
        log.info("Validating email: {}", email.getTo());
        try {
            internetAddress.setAddress(email.getTo());
            internetAddress.validate();
        } catch (AddressException e) {
            throw new InvalidEmailException(email.getTo());
        }
    }
}
