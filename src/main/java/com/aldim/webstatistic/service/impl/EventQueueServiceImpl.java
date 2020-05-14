package com.aldim.webstatistic.service.impl;


import com.aldim.webstatistic.dao.VisitBatchDao;
import com.aldim.webstatistic.dto.SiteStatistic;
import com.aldim.webstatistic.dto.VisitEvent;
import com.aldim.webstatistic.model.Visit;
import com.aldim.webstatistic.service.EventQueueService;
import com.aldim.webstatistic.service.StatisticService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

@Service
public class EventQueueServiceImpl implements EventQueueService {
    private static final Logger logger = LoggerFactory.getLogger(EventQueueServiceImpl.class);

    @Value("${app.events.per.job}")
    private int eventsQuantity = 1000;

    private StatisticService webStatisticService;

    private VisitBatchDao visitBatchDao;

    private LinkedBlockingQueue<Visit> eventQueue;

    private SiteStatistic cachedStatistic = new SiteStatistic();


    @Override
    public SiteStatistic putEvent(VisitEvent event) {
        if (!eventQueue.offer(convert(event))) {
            logger.warn("Queue is full! event will be dropped until queue is available.");
        }
        return getCachedStatistic();
    }

    @Override
    @Scheduled(cron = "${app.events.loadjob.cron}")
    public void update() {
        logger.info("update visits to db started...");
        visitBatchDao.saveAll(pollEvents());
        logger.info("update visits to db finished...");
        updateCachedStatistic();

    }

    private List<Visit> pollEvents() {
        ArrayList<Visit> evets = new ArrayList<>();
        int count = 0;
        while (eventQueue.peek() != null && count < eventsQuantity) {
            evets.add(eventQueue.poll());
            count++;
        }
        return evets;
    }

    public SiteStatistic getCachedStatistic() {
        return cachedStatistic;
    }

    public void updateCachedStatistic() {
        SiteStatistic statistic = webStatisticService.getStatisticForDay(new Date());
        cachedStatistic = statistic;
    }

    private Visit convert(VisitEvent event) {
        Visit visit = new Visit(event.getUserId(), event.getPageId(), new Date());
        return visit;
    }

    @Resource(name = "eventQueue")
    public void setEventQueue(LinkedBlockingQueue<Visit> eventQueue) {
        this.eventQueue = eventQueue;
    }

    @Autowired
    public void setVisitBatchDao(VisitBatchDao visitBatchDao) {
        this.visitBatchDao = visitBatchDao;
    }

    @Autowired
    public void setWebStatisticService(StatisticService webStatisticService) {
        this.webStatisticService = webStatisticService;
    }

    public void setEventsQuantity(int eventsQuantity) {
        this.eventsQuantity = eventsQuantity;
    }
}
