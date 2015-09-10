package com.tianhang.adapp.domain;

/**
 * Created by student on 8/9/15.
 */
public class Customer extends java.util.HashMap<String,String>  {
    public Customer(String id, String name, String address, String credit) {
        put("Id", id);
        put("Name", name);
        put("Address", address);
        put("Credit", credit);
    }
}
