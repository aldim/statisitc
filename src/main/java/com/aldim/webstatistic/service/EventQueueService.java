package com.aldim.webstatistic.service;

import com.aldim.webstatistic.dto.SiteStatistic;
import com.aldim.webstatistic.dto.VisitEvent;
import com.aldim.webstatistic.model.Visit;

import java.util.List;

/**
 * General interface for working with event queue.
 */
public interface EventQueueService {
    /**
     * Put event into the queue
     * @param event
     * @return - cached site statistic
     */
    SiteStatistic putEvent(VisitEvent event);

    /**
     * Load Visits from queue to db
     */
    void update();
}
