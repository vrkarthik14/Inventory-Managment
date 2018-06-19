package com.example.codex_pc.inventoryapp;

import java.util.ArrayList;

public class Supplier {

    private String Name,Address,Email,MobileNo,Company;
    private ArrayList<Product> Products;

    //==============================================================================================

    //Constructor

    public Supplier() {}

    public Supplier(String name, String address, String email, String mobileNo, String company, ArrayList<Product> products) {
        Name = name;
        Address = address;
        Email = email;
        MobileNo = mobileNo;
        Company = company;
        Products = products;
    }

    //==============================================================================================

    //Getters

    public String getName() {
        return Name;
    }

    public String getAddress() {
        return Address;
    }

    public String getEmail() {
        return Email;
    }

    public String getMobileNo() {
        return MobileNo;
    }

    public String getCompany() {
        return Company;
    }

    public ArrayList<Product> getProducts() {
        return Products;
    }

    //==============================================================================================

    //Setter

    public void setName(String name) {
        Name = name;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setMobileNo(String mobileNo) {
        MobileNo = mobileNo;
    }

    public void setCompany(String company) {
        Company = company;
    }

    public void setProducts(ArrayList<Product> products) {
        Products = products;
    }

    //==============================================================================================

}
