package com.example.codex_pc.inventoryapp;

public class Product {

    private String Name, Desc, imagePath, ID, SupplierName;
    private int Price, Quantity, Condition;

    //==============================================================================================

    //Constructor

    public Product(String name, String desc, String imagePath, String ID, String supplierName, int price, int quantity, int condition) {
        Name = name;
        Desc = desc;
        this.imagePath = imagePath;
        this.ID = ID;
        SupplierName = supplierName;
        Price = price;
        Quantity = quantity;
        Condition = condition;
    }

    //==============================================================================================

    //Getters

    public String getName() {
        return Name;
    }

    public String getDesc() {
        return Desc;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getID() {
        return ID;
    }

    public String getSupplierName() {
        return SupplierName;
    }

    public int getPrice() {
        return Price;
    }

    public int getQuantity() {
        return Quantity;
    }

    public int getCondition() {
        return Condition;
    }

    //==============================================================================================

    //Setter

    public void setName(String name) {
        Name = name;
    }

    public void setDesc(String desc) {
        Desc = desc;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setSupplierName(String supplierName) {
        SupplierName = supplierName;
    }

    public void setPrice(int price) {
        Price = price;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public void setCondition(int condition) {
        Condition = condition;
    }

    //==============================================================================================

}
