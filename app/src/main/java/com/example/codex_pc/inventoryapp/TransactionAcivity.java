package com.example.codex_pc.inventoryapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class TransactionAcivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        ArrayList<Transaction> transactions= ((MyAppData)this.getApplication()).getTransactions();
        //Log.i("Check",products.get(0).getName());
        TransactionAdapter transactionAdapter= new TransactionAdapter(this,transactions);
        ListView product_list_view = findViewById(R.id.transactiona_list);
        product_list_view.setAdapter(transactionAdapter);
    }
}
