package com.tianhang.adapp.domain;

/**
 * Created by shenwen on 15/9/15.
 */
public class PurchaseBean {

    private int purchaserId;
    private int supplierId;
    private String purchaseDate;
    private String userId;
    private String expectedDeliveryDate;
    private String status;

    public PurchaseBean(int purchaserId, int supplierId, String purchaseDate, String userId, String expectedDeliveryDate, String status) {
        this.purchaserId = purchaserId;
        this.supplierId = supplierId;
        this.purchaseDate = purchaseDate;
        this.userId = userId;
        this.expectedDeliveryDate = expectedDeliveryDate;
        this.status = status;
    }

    public PurchaseBean() {
    }

    public int getPurchaserId() {

        return purchaserId;
    }

    public void setPurchaserId(int purchaserId) {
        this.purchaserId = purchaserId;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getExpectedDeliveryDate() {
        return expectedDeliveryDate;
    }

    public void setExpectedDeliveryDate(String expectedDeliveryDate) {
        this.expectedDeliveryDate = expectedDeliveryDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}