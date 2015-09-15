package com.tianhang.adapp.domain;

/**
 * Created by shenwen on 15/9/15.
 */
public class discrepancyDetailBean {
    private String type;
    private String categoryName;
    private String description;
    private int balance;
    private String unit;
    private String Remark;
    private String status;

    public discrepancyDetailBean(String type, String categoryName, String description, int balance, String unit, String remark, String status) {
        this.type = type;
        this.categoryName = categoryName;
        this.description = description;
        this.balance = balance;
        this.unit = unit;
        Remark = remark;
        this.status = status;
    }

    public String getType() {

        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
