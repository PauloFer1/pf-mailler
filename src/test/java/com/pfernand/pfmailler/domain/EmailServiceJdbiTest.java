package com.pfernand.pfmailler.domain;

import com.pfernand.pfmailler.model.Email;
import com.pfernand.pfmailler.repository.EmailDAO;
import com.pfernand.pfmailler.rest.exceptions.MaillerException;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.extension.NoSuchExtensionException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@RunWith(MockitoJUnitRunner.class)
public class EmailServiceJdbiTest {

    @Mock
    private Jdbi jdbi;

    private EmailServiceJdbi emailServiceJdbi;

    private static final Email EMAIL = Email.builder()
            .to("to@mail.com")
            .from("from@mail.com")
            .body("body")
            .subject("subject")
            .build();


    @Before
    public void setUp() {
        emailServiceJdbi = new EmailServiceJdbi(jdbi);
    }

    @Test
    public void saveEmailEscalatesJdbiException() {
        // Given

        // When
        Mockito.doThrow(new NoSuchExtensionException("error message"))
                .when(jdbi).useExtension(Mockito.any(), Mockito.any());
        // Then
        assertThatExceptionOfType(MaillerException.class)
                .isThrownBy(() -> emailServiceJdbi.saveEmail(EMAIL))
                .withMessageContaining("error message");
    }

    @Test
    public void saveEmailSuccess() throws MaillerException {
        // Given

        // When
        emailServiceJdbi.saveEmail(EMAIL);

        // Then
        Mockito.verify(jdbi, Mockito.times(1))
                .useExtension(Mockito.eq(EmailDAO.class), Mockito.any());

    }
}