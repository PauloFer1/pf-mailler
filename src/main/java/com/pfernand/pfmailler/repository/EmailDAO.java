package com.pfernand.pfmailler.repository;

import com.pfernand.pfmailler.model.Email;
import org.jdbi.v3.sqlobject.SqlObject;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

public interface EmailDAO extends SqlObject {

    String INSERT_EMAIL =
            "insert into " +
                    "PF_EMAIL(SENT_TIME, TO_EMAIL, FROM_EMAIL, SUBJECT_EMAIL, BODY_EMAIL) " +
                    "VALUES(:sentTime, :to, :from, :subject, :body)";

    @SqlUpdate(INSERT_EMAIL)
    void insertEmail(@BindBean Email email);
}