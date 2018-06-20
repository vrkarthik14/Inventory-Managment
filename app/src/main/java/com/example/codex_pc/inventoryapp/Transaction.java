package com.example.codex_pc.inventoryapp;

public class Transaction {

    private String ProductID, ProductName, Date, Time;
    private int Quantity, Price, isSupply;
    private Supplier supplier;
    private Customer customer;

    //==============================================================================================

        //Constructors

    public Transaction() {}

    public Transaction(String productID, String productName, String date, String time, String name, int quantity, int price, int isSending) {
        ProductID = productID;
        ProductName = productName;
        Date = date;
        Time = time;
        this.isSupply = isSending;
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

    public Supplier getSupplier() {
        return supplier;
    }

    public Customer getCustomer() {
        return customer;
    }

    public int getQuantity() {
        return Quantity;
    }

    public int getPrice() {
        return Price;
    }

    public int getIsSupply() {
        return isSupply;
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

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public void setPrice(int price) {
        Price = price;
    }

    public void setIsSupply(int isSending) {
        this.isSupply = isSending;
    }

    //==============================================================================================
}
