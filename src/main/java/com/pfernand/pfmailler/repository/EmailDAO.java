package com.pfernand.pfmailler.repository;

import com.pfernand.pfmailler.model.Email;
import com.pfernand.pfmailler.model.EmailMapper;
import java.util.List;
import org.jdbi.v3.sqlobject.SqlObject;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

public interface EmailDAO extends SqlObject {

    String INSERT_EMAIL =
            "insert into " +
                    "PF_EMAIL(SENT_TIME, TO_EMAIL, FROM_EMAIL, SUBJECT_EMAIL, BODY_EMAIL) " +
                    "VALUES(:sentTime, :to, :from, :subject, :body)";

    String SELECT_EMAIL =
            "select " +
                "FROM_EMAIL, " +
                "TO_EMAIL, " +
                "SUBJECT_EMAIL, " +
                "BODY_EMAIL, " +
                "SENT_TIME " +
                "from PF_EMAIL";

    @SqlUpdate(INSERT_EMAIL)
    void insertEmail(@BindBean Email email);

    @SqlQuery(SELECT_EMAIL)
    @RegisterRowMapper(EmailMapper.class)
    List<Email> selectAllEmail();
}