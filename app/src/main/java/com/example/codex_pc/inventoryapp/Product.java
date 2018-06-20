package com.example.codex_pc.inventoryapp;

public class Product {

    private String Name, Desc, imagePath, ID, Condition;
    private int Price, Quantity;
    private Supplier supplier;

    //==============================================================================================

    //Constructor

    public Product() {}

    public Product(String name, String desc, String imagePath, String ID, Supplier supplier, int price, int quantity, String condition) {
        Name = name;
        Desc = desc;
        this.imagePath = imagePath;
        this.ID = ID;
        this.supplier = supplier;
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

    public int getPrice() {
        return Price;
    }

    public int getQuantity() {
        return Quantity;
    }

    public String getCondition() {
        return Condition;
    }

    public Supplier getSupplier() {
        return supplier;
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

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public void setPrice(int price) {
        Price = price;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public void setCondition(String condition) {
        Condition = condition;
    }

    //==============================================================================================

}
