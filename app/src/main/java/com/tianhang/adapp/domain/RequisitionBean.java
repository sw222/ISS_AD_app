package com.tianhang.adapp.domain;

/**
 * Created by student on 7/9/15.
 */
public class RequisitionBean {
    private String ID;
    private String depa;
    private String status;
    private String date;

    public RequisitionBean(String ID, String depa, String status, String date) {
        this.ID = ID;
        this.depa = depa;
        this.status = status;
        this.date = date;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setDepa(String depa) {
        this.depa = depa;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDepa() {
        return depa;
    }

    public String getStatus() {
        return status;
    }

    public String getDate() {
        return date;
    }
}
