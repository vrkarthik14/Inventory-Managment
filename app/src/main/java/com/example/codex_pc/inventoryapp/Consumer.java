package com.example.codex_pc.inventoryapp;

public class Consumer {

    private String Name,Address,Email,MobileNo,Company;

    //==============================================================================================

    //Constructor

    public Consumer(String name, String address, String email, String mobileNo, String company) {
        Name = name;
        Address = address;
        Email = email;
        MobileNo = mobileNo;
        Company = company;
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

    //==============================================================================================

}
