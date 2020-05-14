package com.aldim.webstatistic.dao;

import com.aldim.webstatistic.model.Visit;

import java.util.List;

public interface VisitBatchDao {
    void saveAll(List<Visit> visits);
}
