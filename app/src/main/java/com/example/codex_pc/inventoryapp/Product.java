package com.example.codex_pc.inventoryapp;

public class Product {

    private String Name, Desc, imagePath, ID, Condition,supplier_name;
    private int Price, Quantity;

    //==============================================================================================

    //Constructor

    public Product() {}

    public Product(String name, String desc, String imagePath, String ID, String supplier_name, int price, int quantity, String condition) {
        Name = name;
        Desc = desc;
        this.imagePath = imagePath;
        this.ID = ID;
        this.supplier_name = supplier_name;
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

    public String getSupplier_name() {
        return supplier_name;
    }

    public void setSupplier_name(String supplier_name) {
        this.supplier_name = supplier_name;
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
