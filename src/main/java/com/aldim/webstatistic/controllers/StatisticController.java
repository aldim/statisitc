package com.aldim.webstatistic.controllers;

import com.aldim.webstatistic.dto.SiteStatistic;
import com.aldim.webstatistic.dto.VisitEvent;
import com.aldim.webstatistic.service.EventQueueService;
import com.aldim.webstatistic.service.StatisticService;
import com.aldim.webstatistic.service.task.InitialTestLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping(path = "statistics")
public class StatisticController {
    private StatisticService statisticService;

    private EventQueueService eventQueueService;

    @Autowired
    private InitialTestLoader testLoad;

    @Autowired
    public void setStatisticService(StatisticService statisticService) {
        this.statisticService = statisticService;
    }

    @Autowired
    public void setEventQueueService(EventQueueService eventQueueService) {
        this.eventQueueService = eventQueueService;
    }

    @PostMapping(path = "/add")
    public SiteStatistic putStatistic(@RequestBody VisitEvent event) {
        return eventQueueService.putEvent(event);
    }

    @GetMapping()
    public SiteStatistic getStatistic(@RequestParam Date from,
                                      @RequestParam Date to) {
        return statisticService.getStatisticForPeriod(from, to);
    }
}
