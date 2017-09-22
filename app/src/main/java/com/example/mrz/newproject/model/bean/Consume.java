package com.example.mrz.newproject.model.bean;


import java.io.Serializable;

public class Consume implements Serializable {

    private String type;
    private String address;
    private String price;
    private String date;
    private String balance;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return address + "   " + price +"   "+ date + "   "+ balance ;
    }
}
