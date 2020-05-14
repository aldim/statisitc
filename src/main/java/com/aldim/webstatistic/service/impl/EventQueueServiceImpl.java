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
    @Value("${application.batchsize}")
    private int heap;

    private StatisticService webStatisticService;

    private VisitBatchDao visitBatchDao;

    private LinkedBlockingQueue<Visit> eventQueue;

    private SiteStatistic cachedStatistic = new SiteStatistic();


    @Override
    public SiteStatistic putEvent(VisitEvent event) {
        if (!eventQueue.offer(convert(event))) {
            logger.warn("Queue is full! Events will drop until queue is available.");
        }
        return getCachedStatistic();
    }

    @Override
    @Scheduled(cron = "${visitloader.cron}")
    public void update() {
        visitBatchDao.saveAll(pollEvents());
        updateCachedStatistic();
    }

    private List<Visit> pollEvents() {
        ArrayList<Visit> evets = new ArrayList<>();
        int sizeCounter = 0;
        while (eventQueue.peek() != null && sizeCounter < heap) {
            evets.add(eventQueue.poll());
            sizeCounter++;
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
}
