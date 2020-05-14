package com.aldim.webstatistic.dto;

import java.io.Serializable;

public class SiteStatistic implements Serializable {
    private long overallCount;
    private long uniqueUsersCount;
    private long regularUsersCount;

    public SiteStatistic() {

    }

    public SiteStatistic(long overallCount, long uniqueUsersCount, long regularUsersCount) {
        this.overallCount = overallCount;
        this.uniqueUsersCount = uniqueUsersCount;
        this.regularUsersCount = regularUsersCount;
    }

    public long getOverallCount() {
        return overallCount;
    }

    public void setOverallCount(long overallCount) {
        this.overallCount = overallCount;
    }

    public long getUniqueUsersCount() {
        return uniqueUsersCount;
    }

    public void setUniqueUsersCount(long uniqueUsersCount) {
        this.uniqueUsersCount = uniqueUsersCount;
    }

    public long getRegularUsersCount() {
        return regularUsersCount;
    }

    public void setRegularUsersCount(long regularUsersCount) {
        this.regularUsersCount = regularUsersCount;
    }
}
