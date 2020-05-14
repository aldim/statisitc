package com.aldim.webstatistic.dto;

import java.io.Serializable;

public class VisitEvent implements Serializable {
    private String userId;
    private String pageId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPageId() {
        return pageId;
    }

    public void setPageId(String pageId) {
        this.pageId = pageId;
    }
}
