package com.tianhang.adapp.domain;

/**
 * Created by shenwen on 15/9/15.
 */
public class PurchaseItemBean {
    private int Itemcode;
    private String Description;
    private int Quantity;
    private int price;
    private int Amount;

    public PurchaseItemBean() {
    }

    public PurchaseItemBean(int itemcode, String description, int quantity, int price, int amount) {
        Itemcode = itemcode;
        Description = description;
        Quantity = quantity;
        this.price = price;
        Amount = amount;
    }

    public int getItemcode() {

        return Itemcode;
    }

    public void setItemcode(int itemcode) {
        Itemcode = itemcode;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getAmount() {
        return Amount;
    }

    public void setAmount(int amount) {
        Amount = amount;
    }
}
