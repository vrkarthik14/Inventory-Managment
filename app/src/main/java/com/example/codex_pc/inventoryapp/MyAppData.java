package com.example.codex_pc.inventoryapp;

import android.app.Application;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MyAppData extends Application{

    DatabaseReference product_databaseReference,supplier_databaseReference,transaction_databaseReference,customer_databaseReference;
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
        product_databaseReference = FirebaseDatabase.getInstance().getReference().child("products");
        supplier_databaseReference = FirebaseDatabase.getInstance().getReference().child("suppliers");
        transaction_databaseReference = FirebaseDatabase.getInstance().getReference().child("transactions");
        customer_databaseReference = FirebaseDatabase.getInstance().getReference().child("customers");

        product_databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                products.add((Product) dataSnapshot.getValue());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        customer_databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                customers.add((Customer) dataSnapshot.getValue());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Log.i("changed child",dataSnapshot.getValue().toString());

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.i("removed child",dataSnapshot.getValue().toString());

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        supplier_databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                suppliers.add((Supplier) dataSnapshot.getValue());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Log.i("changed child",dataSnapshot.getValue().toString());

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.i("removed child",dataSnapshot.getValue().toString());


            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        transaction_databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                transactions.add((Transaction) dataSnapshot.getValue());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Log.i("changed child",dataSnapshot.getValue().toString());
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.i("remove child",dataSnapshot.getValue().toString());
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    public void pushProducts(Product product){
        product_databaseReference.push().setValue(product);
    }

    public void pushSuplliers(Supplier supplier){
        supplier_databaseReference.push().setValue(supplier);
    }

    public void pushCustomers(Customer customer){
        customer_databaseReference.push().setValue(customer);
    }
    public void pushTransaction(Transaction transaction){
        transaction_databaseReference.push().setValue(transaction);
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
