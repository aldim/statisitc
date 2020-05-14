package com.aldim.webstatistic.service.task;

import com.aldim.webstatistic.dao.VisitBatchDao;
import com.aldim.webstatistic.dao.VisitDao;
import com.aldim.webstatistic.dto.VisitEvent;
import com.aldim.webstatistic.model.Visit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

@Service
public class TestLoad {

    private static Random random = new Random();
    @Autowired
    private VisitDao visitDao;
    @Autowired
    private VisitBatchDao visitBatchDao;

    public void load() {
        visitBatchDao.saveAll(getVisits());
    }

    private ArrayList<VisitEvent> getVisitEvents() {
        ArrayList<VisitEvent> events = new ArrayList<>(1_000_000);
        String customer_prefix = "id_";
        String page_prefix = "id_";
        for (int i = 0; i < 1_000_000; i++) {
            VisitEvent event = new VisitEvent();
            event.setUserId(customer_prefix + random.nextInt(10_00));
            event.setPageId(page_prefix + random.nextInt(50));
            events.add(event);
        }
        return events;
    }

    private ArrayList<Visit> getVisits() {
        ArrayList<Visit> events = new ArrayList<>(1_000_000);
        String customer_prefix = "id_";
        String page_prefix = "id_";
        for (int i = 0; i < 1_000_000; i++) {
            Visit event = new Visit();
            event.setUserId(customer_prefix + random.nextInt(10_000));
            event.setPageId(page_prefix + random.nextInt(50));
            event.setDate(new Date());
            events.add(event);
        }
        return events;
    }
}
