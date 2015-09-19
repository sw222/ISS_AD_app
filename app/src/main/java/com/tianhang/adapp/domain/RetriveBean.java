package com.tianhang.adapp.domain;

/**
 * Created by student on 18/9/15.
 */
public class RetriveBean {

    private int itemID;
    private int amount;
    private String category;
    private String itemName;
    private String unit;
    private String bin;

    public RetriveBean() {
    }

    public int getItemID() {
        return itemID;
    }

    public int getAmount() {
        return amount;
    }

    public String getCategory() {
        return category;
    }

    public String getItemName() {
        return itemName;
    }

    public String getUnit() {
        return unit;
    }

    public String getBin() {
        return bin;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public void setBin(String bin) {
        this.bin = bin;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "RetriveBean{" +
                "itemID=" + itemID +
                ", amount=" + amount +
                ", category='" + category + '\'' +
                ", itemName='" + itemName + '\'' +
                ", unit='" + unit + '\'' +
                ", bin='" + bin + '\'' +
                '}';
    }
}
