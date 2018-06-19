package com.example.codex_pc.inventoryapp;

import android.app.Application;

import java.util.ArrayList;

public class MyAppData extends Application{

    ArrayList<Product> products;
    ArrayList<Supplier> suppliers;
    ArrayList<Customer> customers;
    ArrayList<Transaction> transactions;

    @Override
    public void onCreate() {
        super.onCreate();

        products = new ArrayList<>();
        suppliers = new ArrayList<>();
        customers = new ArrayList<>();
        transactions = new ArrayList<>();

    }
}
