package com.aldim.webstatistic.dao;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Resource
public interface VisitDao {
    long countOfVisitForPeriod(Date from, Date to);

    long countOfVisitorsForPeriod(Date from, Date to);

    List<String> findRegularUsersForPeriod(Date from, Date to);
}
