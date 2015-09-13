package com.tianhang.adapp.domain;

/**
 * Created by student on 7/9/15.
 */
public class RequisitionBean {
    private String requisitionID;
    private String department;
    private String userID;
    private String status;
    private String requestDate;
    private String rejectReason;

    public RequisitionBean() {
    }

    @Override
    public String toString() {
        return "RequisitionBean{" +
                "requisitionID='" + requisitionID + '\'' +
                ", departmentID='" + department + '\'' +
                ", userID='" + userID + '\'' +
                ", status='" + status + '\'' +
                ", requestDate='" + requestDate + '\'' +
                ", rejectReason='" + rejectReason + '\'' +
                '}';
    }

    public RequisitionBean(String requisitionID, String departmentID, String userID, String status, String requestDate, String rejectReason) {
        this.requisitionID = requisitionID;
        this.department = departmentID;
        this.userID = userID;
        this.status = status;
        this.requestDate = requestDate;
        this.rejectReason = rejectReason;
    }

    public String getRequisitionID() {
        return requisitionID;
    }

    public String getDepartmentID() {
        return department;
    }

    public String getUserID() {
        return userID;
    }

    public String getStatus() {
        return status;
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
        this.department = departmentID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }
}
