package com.aldim.webstatistic.service.impl;

import com.aldim.webstatistic.dao.VisitDao;
import com.aldim.webstatistic.dto.SiteStatistic;
import com.aldim.webstatistic.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class StatisticServiceImpl implements StatisticService {
    private VisitDao visitDao;

    @Autowired
    public void setVisitDao(VisitDao visitDao) {
        this.visitDao = visitDao;
    }

    @Override
    public SiteStatistic getStatisticForDay(Date day) {
        return getStatisticForPeriod(day, day);
    }

    @Override
    public SiteStatistic getStatisticForPeriod(Date from, Date to) {
        long average = visitDao.countOfVisitForPeriod(from, to);
        long uniqueUsers = visitDao.countOfVisitorsForPeriod(from, to);
        long regularUsers = visitDao.findRegularUsersForPeriod(from, to).size();

        return new SiteStatistic(average, uniqueUsers, regularUsers);
    }
}
