package com.tianhang.adapp.domain;

/**
 * Created by student on 7/9/15.
 */
public class RequisitionBean {
    private String requisitionID;
    private String departmentID;
    private String departmentName;
    private String userID;
    private String status;
    private String requestDate;
    private String rejectReason;

    public RequisitionBean() {
    }

    public RequisitionBean(String requisitionID, String departmentID, String departmentName, String userID, String status, String requestDate, String rejectReason) {
        this.requisitionID = requisitionID;
        this.departmentID = departmentID;
        this.departmentName = departmentName;
        this.userID = userID;
        this.status = status;
        this.requestDate = requestDate;
        this.rejectReason = rejectReason;
    }

    public String getRequisitionID() {
        return requisitionID;
    }

    public String getDepartmentID() {
        return departmentID;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public String getStatus() {
        return status;
    }

    public String getUserID() {
        return userID;
    }

    public String getRequestDate() {
        return requestDate;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRequisitionID(String requisitionID) {
        this.requisitionID = requisitionID;
    }

    public void setDepartmentID(String departmentID) {
        this.departmentID = departmentID;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }
}