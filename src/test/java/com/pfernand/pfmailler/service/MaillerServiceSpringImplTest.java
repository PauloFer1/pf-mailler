package com.pfernand.pfmailler.service;

import com.pfernand.pfmailler.domain.EmailServiceJdbi;
import com.pfernand.pfmailler.domain.validation.EmailValidatorMx;
import com.pfernand.pfmailler.model.Email;
import com.pfernand.pfmailler.rest.exceptions.MaillerException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class MaillerServiceSpringImplTest {

    @Mock
    private JavaMailSender javaMailSender;

    @Mock
    private EmailServiceJdbi emailSaver;

    @Mock
    private EmailValidatorMx emailValidatorMx;

    private EmailSenderServiceSpringImpl maillerServiceSpring;

    private static final String FROM_EMAIL = "from@mail.com";
    private static final String TO_EMAIL = "to@mail.com";
    private static final String SUBJECT = "subject";
    private static final String BODY = "body";
    private Email email;


    @Before
    public void setUp() {
        maillerServiceSpring = new EmailSenderServiceSpringImpl(javaMailSender, emailSaver, emailValidatorMx);
        email = Email.builder()
                .from(FROM_EMAIL)
                .body(BODY)
                .to(TO_EMAIL)
                .subject(SUBJECT)
                .build();
    }

    @Test
    public void sendSimpleMessageThrowException() {
        // Given
        String message = "Email failed to send";

        // When
        Mockito.doThrow(new MailAuthenticationException(message)).when(javaMailSender)
            .send(Mockito.any(SimpleMailMessage.class));

        //Then
        assertThatExceptionOfType(MaillerException.class)
            .isThrownBy(() -> maillerServiceSpring.sendSimpleMessage(email))
            .withMessageContaining(message);
    }

    @Test
    public void sendSimpleMessageAndSaveThrowException() throws MaillerException {
        // Given
        String message = "Email failed to send";

        // When
        Mockito.doThrow(new MailAuthenticationException(message)).when(javaMailSender)
            .send(Mockito.any(SimpleMailMessage.class));

        //Then
        assertThatExceptionOfType(MaillerException.class)
            .isThrownBy(() -> maillerServiceSpring.sendSimpleMessage(email))
            .withMessageContaining(message);
        Mockito.verify(emailSaver, Mockito.times(1)).saveEmail(email);
    }

    @Test
    public void sendSimpleMessageAndSaveAllwaysSaveEmailAndSetTime() throws MaillerException {
        // Given

        // When
        maillerServiceSpring.sendSimpleMessage(email);

        //Then
        assertNotNull(email.getSentTime());
        Mockito.verify(emailSaver, Mockito.times(1)).saveEmail(email);

    }
}