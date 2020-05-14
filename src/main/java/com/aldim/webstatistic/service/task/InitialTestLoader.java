package com.aldim.webstatistic.service.task;

import com.aldim.webstatistic.dao.VisitBatchDao;
import com.aldim.webstatistic.model.Visit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

@Service
public class InitialTestLoader implements CommandLineRunner {
    private static Logger log = LoggerFactory.getLogger(InitialTestLoader.class);
    private Random random = new Random();
    private String USER_ = "user_";
    private String PAGE = "page_";

    @Value("${app.initial.events.quantity}")
    private int quantity = 1000;

    @Value("${app.initial.events}")
    private boolean initialize;

    private VisitBatchDao visitBatchDao;

    @Autowired
    public void setVisitBatchDao(VisitBatchDao visitBatchDao) {
        this.visitBatchDao = visitBatchDao;
    }

    @Override
    public void run(String... args) throws Exception {
        if (initialize) {
            log.info("Loading {} events....", quantity);
            visitBatchDao.saveAll(getVisits());
            log.info("Loading finished.");
        }
    }

    private ArrayList<Visit> getVisits() {
        ArrayList<Visit> events = new ArrayList<>(quantity);
        Date now = new Date();
        Date dayAgo = Date.from(Instant.now().minus(Duration.ofDays(1)));

        for (int i = 0; i < quantity; i++) {
            events.add(new Visit(USER_ + random.nextInt(100),
                    PAGE + random.nextInt(20),
                    getRandomDate(now, dayAgo)));
        }

        return events;
    }

    private Date getRandomDate(Date one, Date two) {
        return random.nextInt(5) % 2 == 0
                ? one
                : two;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setInitialize(boolean initialize) {
        this.initialize = initialize;
    }
}
