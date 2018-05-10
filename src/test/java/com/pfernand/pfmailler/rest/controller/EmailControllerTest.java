package com.pfernand.pfmailler.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pfernand.pfmailler.domain.EmailService;
import com.pfernand.pfmailler.model.Email;
import com.pfernand.pfmailler.rest.exceptions.MaillerException;
import com.pfernand.pfmailler.service.EmailSenderServiceSpringImpl;
import java.time.Clock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;


@RunWith(MockitoJUnitRunner.class)
public class EmailControllerTest {

    private Clock clock = Clock.systemUTC();

    private ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private EmailSenderServiceSpringImpl maillerServiceSpring;

    @Mock
    private EmailService emailService;

    private EmailController emailController;

    @Before
    public void setUp() {
        emailController = new EmailController(clock, objectMapper, maillerServiceSpring, emailService);
    }

    @Test
    public void sendEmailReturnResponseOk() throws Exception {
        // Given
        Email email = Email.builder()
            .from("test.from@mail.com")
            .to("test.to@mail.com")
            .body("Body text")
            .subject("subject")
            .build();

        // When
        Mockito.doNothing().when(maillerServiceSpring).sendSimpleMessage(email);
        ResponseEntity response = emailController.sendEmail(email);

        // Then
        //ToDo - implement complete request response test
        assertThat(response.getStatusCode())
            .isEqualTo(HttpStatus.OK);
        assertThat(response.getBody())
            .hasFieldOrProperty("inputParameters");
    }

    @Test
    public void sendEmailThrowsMaillerException() throws MaillerException {
        // Given
        Email email = Email.builder()
            .from("test.from@mail.com")
            .to("test.to@mail.com")
            .body("Body text")
            .subject("subject")
            .build();

        // When
        Mockito.doThrow(new MaillerException("No can do.."))
            .when(maillerServiceSpring).sendSimpleMessage(email);

        // Then
        assertThatExceptionOfType(MaillerException.class)
            .isThrownBy(() -> emailController.sendEmail(email))
            .withMessageContaining("No can do..");
    }

    @Test
    public void getEmailsReturnResponseOk() throws Exception {
        // Given

        // When
        ResponseEntity response = emailController.getEmails();

        // Then
        assertThat(response.getStatusCode())
            .isEqualTo(HttpStatus.OK);
    }
}