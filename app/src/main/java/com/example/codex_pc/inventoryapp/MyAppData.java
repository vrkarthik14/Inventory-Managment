package com.example.codex_pc.inventoryapp;

import android.app.Application;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Objects;

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

    int selection = 0;

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
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, String s) {
                products.add(dataSnapshot.getValue(Product.class));
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        customer_databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, String s) {
                customers.add(dataSnapshot.getValue(Customer.class));
                try {
                    CustomerActivity.customers = customers;
                    CustomerActivity.customerAdapter.notifyDataSetChanged();
                } catch (Exception e){
                    Log.d("ErrorHandler",e.toString());
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, String s) {
                Log.i("changed child", Objects.requireNonNull(dataSnapshot.getValue()).toString());

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                Log.i("removed child", Objects.requireNonNull(dataSnapshot.getValue()).toString());

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        supplier_databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, String s) {
                suppliers.add( dataSnapshot.getValue(Supplier.class));
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, String s) {
                Log.i("changed child", Objects.requireNonNull(dataSnapshot.getValue()).toString());

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                Log.i("removed child", Objects.requireNonNull(dataSnapshot.getValue()).toString());


            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        transaction_databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, String s) {
                transactions.add( dataSnapshot.getValue(Transaction.class));
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, String s) {
                Log.i("changed child", Objects.requireNonNull(dataSnapshot.getValue()).toString());
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                Log.i("remove child", Objects.requireNonNull(dataSnapshot.getValue()).toString());
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void pushProduct(Product product){
        product_databaseReference.push().setValue(product);
    }

    public void pushSupllier(Supplier supplier){
        supplier_databaseReference.push().setValue(supplier);
    }

    public void pushCustomer(Customer customer){
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

    public int getSelection() {
        return selection;
    }

    public void setSelection(int selection) {
        this.selection = selection;
    }

    //==============================================================================================

        //Getters for arraylists

    public ArrayList<Product> getProducts() {
        return products;
    }

    public ArrayList<Supplier> getSuppliers() {
        return suppliers;
    }

    public ArrayList<Customer> getCustomers() {
        return customers;
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    //==============================================================================================
}
