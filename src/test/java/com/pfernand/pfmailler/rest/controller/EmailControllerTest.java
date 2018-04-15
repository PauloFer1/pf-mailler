package com.pfernand.pfmailler.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pfernand.pfmailler.domain.EmailService;
import com.pfernand.pfmailler.model.Email;
import com.pfernand.pfmailler.service.EmailSenderServiceSpringImpl;
import java.time.Clock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import static org.assertj.core.api.Assertions.assertThat;


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
        Mockito.doNothing().when(maillerServiceSpring).sendSimpleMessageAndSave(email);
        ResponseEntity response = emailController.sendEmail(email);

        // Then
        //ToDo - implement complete request response test
        assertThat(response.getBody())
            .hasFieldOrProperty("inputParameters");
    }
}