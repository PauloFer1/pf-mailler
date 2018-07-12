package com.pfernand.pfmailler.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.pfernand.pfmailler.model.validator.ValidateEmailField;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

public class EmailMapper implements RowMapper<Email> {


    @Override
    public Email map(ResultSet rs, StatementContext ctx) throws SQLException {

        return Email.builder()
                .from(rs.getString("FROM_EMAIL"))
                .to(rs.getString("TO_EMAIL"))
                .body(rs.getString("BODY_EMAIL"))
                .subject(rs.getString("SUBJECT_EMAIL"))
                .sentTime(ValidateEmailField.validateAndGet(rs.getString("SENT_TIME")))
                .createdAt(ValidateEmailField.validateAndGet(rs.getString("CREATED_AT")))
                .valid(rs.getBoolean("IS_VALID"))
                .build();
    }
}
