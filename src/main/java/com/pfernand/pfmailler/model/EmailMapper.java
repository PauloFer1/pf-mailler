package com.pfernand.pfmailler.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

public class EmailMapper implements RowMapper<Email> {
    @Override
    public Email map(ResultSet rs, StatementContext ctx) throws SQLException {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return Email.builder()
                .from(rs.getString("FROM_EMAIL"))
                .to(rs.getString("TO_EMAIL"))
                .body(rs.getString("BODY_EMAIL"))
                .subject(rs.getString("SUBJECT_EMAIL"))
                .sentTime(LocalDateTime.parse(rs.getString("SENT_TIME"), formatter))
                .createdAt(LocalDateTime.parse(rs.getString("CREATED_AT"), formatter))
                .valid(rs.getBoolean("IS_VALID"))
                .build();
    }
}
