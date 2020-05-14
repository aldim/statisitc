package com.aldim.webstatistic.dao.impl;

import com.aldim.webstatistic.dao.VisitBatchDao;
import com.aldim.webstatistic.model.Visit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class VisitBatchDaoImpl implements VisitBatchDao {
    private static final Logger log = LoggerFactory.getLogger(VisitBatchDaoImpl.class);
    private static int BATCH_SIZE = 1000;
    private static int USER_ID = 1;
    private static int PAGE_ID = 2;
    private static int DATE = 3;

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public VisitBatchDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void saveAll(List<Visit> events) {
        log.debug("%s events start loading." + events.size());
        int[][] updateCounts = jdbcTemplate.batchUpdate(
                "insert into visit (user_id, page_id, date) values (?,?,?)",
                events,
                BATCH_SIZE,
                (ps, event) -> {
                    ps.setString(USER_ID, event.getUserId());
                    ps.setString(PAGE_ID, event.getPageId());
                    ps.setDate(DATE, convertUtilToSql(event.getDate()));
                });
    }

    private java.sql.Date convertUtilToSql(java.util.Date uDate) {
        java.sql.Date sDate = new java.sql.Date(uDate.getTime());
        return sDate;
    }
}
