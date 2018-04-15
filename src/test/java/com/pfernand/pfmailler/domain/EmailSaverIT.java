package com.pfernand.pfmailler.domain;

import com.pfernand.pfmailler.H2Config;
import com.pfernand.pfmailler.PfMaillerApplication;
import com.pfernand.pfmailler.model.Email;
import com.pfernand.pfmailler.rest.exceptions.MaillerException;
import org.jdbi.v3.core.Jdbi;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;


@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {PfMaillerApplication.class, H2Config.class}
, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmailSaverIT {

    @Autowired
    EmailSaver emailSaver;

    @Autowired
    Jdbi jdbi;

    @Test
    public void saveEmailStoreInDb() throws MaillerException {
        // Given
        Email email = Email.builder()
            .from("test.from@mail.com")
            .to("test.to@mail.com")
            .body("Body text")
            .subject("subject")
            .build();

        // When
        emailSaver.saveEmail(email);

        // Then
        Email emailResult = getEmail();
        Assert.assertEquals(email, emailResult);
    }

    private Email getEmail() {
        return jdbi.withHandle(h -> h.createQuery(
            "SELECT \n" +
                "\tFROM_EMAIL AS `from`,\n" +
                "\tTO_EMAIL AS `to`,\n" +
                "  SUBJECT_EMAIL AS subject, \n" +
                "\tBODY_EMAIL AS body, \n" +
                "\tSENT_TIME\n" +
                "FROM PF_EMAIL;")
            .mapToBean(Email.class).findFirst()).get();
    }
}