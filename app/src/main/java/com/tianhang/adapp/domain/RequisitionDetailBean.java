package com.tianhang.adapp.domain;

/**
 * Created by student on 14/9/15.
 */
public class RequisitionDetailBean {

    private String description;
    private String unit;
    private String name;
    private String status;
    private String collectionPoint;
    private String reqDate;
    private int number;
    private String photourl;

    public RequisitionDetailBean(String description, String unit, String name, String status, String collectionPoint, String reqDate, int number, String photourl) {
        this.description = description;
        this.unit = unit;
        this.name = name;
        this.status = status;
        this.collectionPoint = collectionPoint;
        this.reqDate = reqDate;
        this.number = number;
        this.photourl = photourl;
    }

    public String getDescription() {
        return description;
    }

    public String getPhotourl() {
        return photourl;
    }

    public int getNumber() {
        return number;
    }

    public String getReqDate() {
        return reqDate;
    }

    public String getCollectionPoint() {
        return collectionPoint;
    }

    public String getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

    public String getUnit() {
        return unit;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCollectionPoint(String collectionPoint) {
        this.collectionPoint = collectionPoint;
    }

    public void setReqDate(String reqDate) {
        this.reqDate = reqDate;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setPhotourl(String photourl) {
        this.photourl = photourl;
    }
}
