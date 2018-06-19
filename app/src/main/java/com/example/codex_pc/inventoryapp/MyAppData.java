package com.example.codex_pc.inventoryapp;

import android.app.Application;

import java.util.ArrayList;

public class MyAppData extends Application{

    ArrayList<Product> products;
    ArrayList<Supplier> suppliers;
    ArrayList<Customer> customers;
    ArrayList<Transaction> transactions;

    Product product;
    Supplier supplier;
    Customer customer;
    Transaction transaction;

    @Override
    public void onCreate() {
        super.onCreate();

        products = new ArrayList<>();
        suppliers = new ArrayList<>();
        customers = new ArrayList<>();
        transactions = new ArrayList<>();

    }

    //==============================================================================================

        //Setters and Getters for current items

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    //==============================================================================================
}
