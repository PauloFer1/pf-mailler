package com.pfernand.pfmailler.domain.validation;

import com.pfernand.pfmailler.domain.exceptions.InvalidEmailException;
import com.pfernand.pfmailler.model.Email;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;


@RunWith(MockitoJUnitRunner.class)
public class EmailValidatorJavaMailTest {

    @Spy
    private InternetAddress internetAddress = new InternetAddress();

    private EmailValidatorJavaMail emailValidatorJavaMail;

    @Before
    public void setUp() {
        emailValidatorJavaMail = new EmailValidatorJavaMail(internetAddress);
    }

    @Test
    public void isValidEmailTestValidEmail() throws Exception {
        // Given
        String validEmail = "paulo.fernandes@gmail.com";
        Email email = Email.builder()
                .to(validEmail)
                .build();

        // When
        emailValidatorJavaMail.isValidEmail(email);

        // Then
        Mockito.verify(internetAddress).setAddress(Mockito.eq(validEmail));
        Mockito.verify(internetAddress).validate();
    }

    @Test
    public void isValidEmailTestInvalidEmailThrowsException() {
        // Given
        String invalidEmail = "paulo.fernandes@gmail.";
        Email email = Email.builder()
                .to(invalidEmail)
                .build();

        // When
        // Then
        assertThatExceptionOfType(AddressException.class)
                .isThrownBy(() -> emailValidatorJavaMail.isValidEmail(email))
                .withMessageContaining("Domain ends with dot");
    }
}