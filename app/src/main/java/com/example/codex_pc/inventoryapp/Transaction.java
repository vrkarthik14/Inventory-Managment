package com.example.codex_pc.inventoryapp;

public class Transaction {

    private String ProductID, ProductName, Date, Time, Supplier, Customer;
    private int Quantity, Price;

    //==============================================================================================

        //Constructors

    public Transaction(String productID, String productName, String date, String time, String name, int quantity, int price, int isSending) {
        ProductID = productID;
        ProductName = productName;
        Date = date;
        Time = time;
        if(isSending==1){
            Supplier = name;
        } else {
            Customer = name;
        }
        Quantity = quantity;
        Price = price;
    }

    //==============================================================================================

        //Getters

    public String getProductID() {
        return ProductID;
    }

    public String getProductName() {
        return ProductName;
    }

    public String getDate() {
        return Date;
    }

    public String getTime() {
        return Time;
    }

    public String getSupplier() {
        return Supplier;
    }

    public String getCustomer() {
        return Customer;
    }

    public int getQuantity() {
        return Quantity;
    }

    public int getPrice() {
        return Price;
    }

    //==============================================================================================

        //Setters

    public void setProductID(String productID) {
        ProductID = productID;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public void setDate(String date) {
        Date = date;
    }

    public void setTime(String time) {
        Time = time;
    }

    public void setSupplier(String supplier) {
        Supplier = supplier;
    }

    public void setCustomer(String customer) {
        Customer = customer;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public void setPrice(int price) {
        Price = price;
    }

    //==============================================================================================
}
