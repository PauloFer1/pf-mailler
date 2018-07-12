package com.pfernand.pfmailler.model;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.jdbi.v3.core.statement.StatementContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class EmailMapperTest {

    @Mock
    private ResultSet resultSet;

    @Mock
    private StatementContext statementContext;

    private EmailMapper emailMapper = new EmailMapper();

    private static final String FROM_EMAIL = "from@mail.com";
    private static final String TO_EMAIL = "to@mail.com";
    private static final String BODY_EMAIL = "body";
    private static final String SUBJECT_EMAIL = "subject";
    private static final String SENT_TIME = "2018-01-01 10:10:10";
    private static final boolean VALID = true;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Test
    public void mapFillAllFields() throws SQLException {
        // Given

        // When
        Mockito.when(resultSet.getString("FROM_EMAIL")).thenReturn(FROM_EMAIL);
        Mockito.when(resultSet.getString("TO_EMAIL")).thenReturn(TO_EMAIL);
        Mockito.when(resultSet.getString("BODY_EMAIL")).thenReturn(BODY_EMAIL);
        Mockito.when(resultSet.getString("SUBJECT_EMAIL")).thenReturn(SUBJECT_EMAIL);
        Mockito.when(resultSet.getString("SENT_TIME")).thenReturn(SENT_TIME);
        Mockito.when(resultSet.getString("CREATED_AT")).thenReturn(SENT_TIME);
        Mockito.when(resultSet.getBoolean("IS_VALID")).thenReturn(VALID);
        Email email = emailMapper.map(resultSet, statementContext);

        // Then
        assertEquals(email.getFrom(), FROM_EMAIL);
        assertEquals(email.getTo(), TO_EMAIL);
        assertEquals(email.getBody(), BODY_EMAIL);
        assertEquals(email.getSubject(), SUBJECT_EMAIL);
        assertEquals(email.getSentTime(), LocalDateTime.parse(SENT_TIME, formatter));
        assertEquals(email.getCreatedAt(), LocalDateTime.parse(SENT_TIME, formatter));
        assertTrue(email.isValid());
    }

    @Test
    public void mapDatesCanBeNull() throws SQLException {
        // Given

        // When
        Mockito.when(resultSet.getString("FROM_EMAIL")).thenReturn(FROM_EMAIL);
        Mockito.when(resultSet.getString("TO_EMAIL")).thenReturn(TO_EMAIL);
        Mockito.when(resultSet.getString("BODY_EMAIL")).thenReturn(BODY_EMAIL);
        Mockito.when(resultSet.getString("SUBJECT_EMAIL")).thenReturn(SUBJECT_EMAIL);
        Mockito.when(resultSet.getString("SENT_TIME")).thenReturn(null);
        Mockito.when(resultSet.getString("CREATED_AT")).thenReturn(null);
        Mockito.when(resultSet.getBoolean("IS_VALID")).thenReturn(VALID);
        Email email = emailMapper.map(resultSet, statementContext);

        // Then
        assertEquals(email.getFrom(), FROM_EMAIL);
        assertEquals(email.getTo(), TO_EMAIL);
        assertEquals(email.getBody(), BODY_EMAIL);
        assertEquals(email.getSubject(), SUBJECT_EMAIL);
        assertEquals(email.getSentTime(), null);
        assertEquals(email.getCreatedAt(), null);
        assertTrue(email.isValid());
    }

}