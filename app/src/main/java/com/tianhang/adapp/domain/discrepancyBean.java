package com.tianhang.adapp.domain;

/**
 * Created by shenwen on 15/9/15.
 */
public class discrepancyBean {

    private int discrepancyId;
    private String reportDate;
    private String remark;
    private String userId;
    private int totalPrice;
    private String status;

    public discrepancyBean() {
    }

    public discrepancyBean(int discrepancyId, String reportDate, String remark, String userId, int totalPrice, String status) {
        this.discrepancyId = discrepancyId;
        this.reportDate = reportDate;
        this.remark = remark;
        this.userId = userId;
        this.totalPrice = totalPrice;
        this.status = status;
    }

    public int getDiscrepancyId() {

        return discrepancyId;
    }

    public void setDiscrepancyId(int discrepancyId) {
        this.discrepancyId = discrepancyId;
    }

    public String getReportDate() {
        return reportDate;
    }

    public void setReportDate(String reportDate) {
        this.reportDate = reportDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
