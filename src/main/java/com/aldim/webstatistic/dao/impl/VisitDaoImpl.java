package com.aldim.webstatistic.dao.impl;

import com.aldim.webstatistic.dao.VisitDao;
import com.aldim.webstatistic.model.Visit;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class VisitDaoImpl implements VisitDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public long countOfVisitForPeriod(Date from, Date to) {
        Query query = entityManager.createQuery(
                "select count(v.id) from Visit v " +
                        "where (v.date >= :from and v.date <= :to)");
        setParams(from, to, query);

        Number count = (Number) query.getSingleResult();
        return count.longValue();
    }

    @Override
    public long countOfVisitorsForPeriod(Date from, Date to) {
        Query query = entityManager.createQuery(
                "select count(distinct v.userId) from Visit v " +
                        "where (v.date >= :from and v.date <= :to)");
        setParams(from, to, query);

        Number count = (Number) query.getSingleResult();
        return count.longValue();
    }

    @Override
    public List<String> findRegularUsersForPeriod(Date from, Date to) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("regular_users_for_period");
        query.registerStoredProcedureParameter(1, Date.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(2, Date.class, ParameterMode.IN);
        query.setParameter(1, from);
        query.setParameter(2, to);

        List<String> resultList = query.getResultList();
        return Optional.ofNullable(resultList).orElse(Collections.emptyList());
    }

    private void setParams(Date from, Date to, Query query) {
        query.setParameter("from", from);
        query.setParameter("to", to);
    }
}
