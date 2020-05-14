package com.aldim.webstatistic.service;

import com.aldim.webstatistic.dto.SiteStatistic;
import com.aldim.webstatistic.dto.VisitEvent;
import com.aldim.webstatistic.model.Visit;

import java.util.List;

public interface EventQueueService {
    SiteStatistic putEvent(VisitEvent event);

    void update();
}
