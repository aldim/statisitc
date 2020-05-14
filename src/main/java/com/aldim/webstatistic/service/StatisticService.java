package com.aldim.webstatistic.service;

import com.aldim.webstatistic.dto.SiteStatistic;

import java.util.Date;

/**
 * General service for storing and getting information about site traffics.
 */
public interface StatisticService {
    SiteStatistic getStatisticForPeriod(Date from, Date to);

    SiteStatistic getStatisticForDay(Date day);
}
