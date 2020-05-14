package com.aldim.webstatistic.model;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Entity
@Table(name = "visit")
public class Visit implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "page_id")
    private String pageId;

    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;

    public Visit() {
    }

    public Visit(String userId, String pageId, Date date) {
        this.userId = userId;
        this.pageId = pageId;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String customerId) {
        this.userId = customerId;
    }

    public String getPageId() {
        return pageId;
    }

    public void setPageId(String webPageId) {
        this.pageId = webPageId;
    }
}
