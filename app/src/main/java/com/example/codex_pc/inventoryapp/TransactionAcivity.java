package com.example.codex_pc.inventoryapp;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class TransactionAcivity extends AppCompatActivity {

    ArrayList<Transaction> transactions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        final SwipeRefreshLayout refreshLayout1 = findViewById(R.id.swiperefresh3);

       transactions = ((MyAppData)this.getApplication()).getTransactions();
        //Log.i("Check",products.get(0).getName());
        final TransactionAdapter transactionAdapter= new TransactionAdapter(this,transactions);
        ListView product_list_view = findViewById(R.id.transactiona_list);
        product_list_view.setAdapter(transactionAdapter);

        refreshLayout1.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                transactions = ((MyAppData)getApplication()).getTransactions();
                transactionAdapter.notifyDataSetChanged();
                refreshLayout1.setRefreshing(false);
            }
        });


    }
}
