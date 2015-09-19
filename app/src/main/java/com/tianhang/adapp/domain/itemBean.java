package com.tianhang.adapp.domain;

/**
 * Created by shenwen on 15/9/15.
 */
public class itemBean {

    private int itemId;
    private String unit;
    private String categoryName;
    private String description;
    private int reorderlevel;
    private int reorderQty;
    private int balance;
    private String binNumber;
    private String photourl;
    private String status;
    private String companyName;

    public itemBean(int itemId, String unit, String categoryName, String description, int reorderlevel, int reorderQty, int balance, String binNumber, String photourl, String status, String companyName) {
        this.itemId = itemId;
        this.unit = unit;
        this.categoryName = categoryName;
        this.description = description;
        this.reorderlevel = reorderlevel;
        this.reorderQty = reorderQty;
        this.balance = balance;
        this.binNumber = binNumber;
        this.photourl = photourl;
        this.status = status;
        this.companyName = companyName;
    }

    public itemBean() {
    }
    public int getItemId() {

        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getReorderlevel() {
        return reorderlevel;
    }

    public void setReorderlevel(int reorderlevel) {
        this.reorderlevel = reorderlevel;
    }

    public int getReorderQty() {
        return reorderQty;
    }

    public void setReorderQty(int reorderQty) {
        this.reorderQty = reorderQty;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getBinNumber() {
        return binNumber;
    }

    public void setBinNumber(String binNumber) {
        this.binNumber = binNumber;
    }

    public String getPhotourl() {
        return photourl;
    }

    public void setPhotourl(String photourl) {
        this.photourl = photourl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Override
    public String toString() {
        return "itemBean{" +
                "itemId=" + itemId +
                ", unit='" + unit + '\'' +
                ", categoryName='" + categoryName + '\'' +
                ", description='" + description + '\'' +
                ", reorderlevel=" + reorderlevel +
                ", reorderQty=" + reorderQty +
                ", balance=" + balance +
                ", binNumber='" + binNumber + '\'' +
                ", photourl='" + photourl + '\'' +
                ", status='" + status + '\'' +
                ", companyName='" + companyName + '\'' +
                '}';
    }

}
