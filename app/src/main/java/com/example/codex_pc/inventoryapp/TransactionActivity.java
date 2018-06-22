package com.example.codex_pc.inventoryapp;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class TransactionActivity extends AppCompatActivity {

    ArrayList<Transaction> transactions;
    SwipeRefreshLayout refreshLayout1;
    TransactionAdapter transactionAdapter;
    TextView transactionTotal;

    @Override
    protected void onStart() {
        super.onStart();

        if (transactions!=null && transactionAdapter!=null) {
            transactions = ((MyAppData)TransactionActivity.this.getApplication()).getTransactions();
            transactionAdapter.notifyDataSetChanged();
            refreshTransactionTotal();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        Toast.makeText(getApplicationContext(), "Swipe to refresh and load data", Toast.LENGTH_SHORT).show();

        refreshLayout1 = findViewById(R.id.swiperefresh3);
        transactionTotal = findViewById(R.id.transaction_count_total);

        transactions = ((MyAppData)this.getApplication()).getTransactions();

        //Log.i("Check",products.get(0).getName());
        transactionAdapter= new TransactionAdapter(this,transactions);
        ListView product_list_view = findViewById(R.id.transactiona_list);
        product_list_view.setAdapter(transactionAdapter);

        refreshLayout1.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                transactions = ((MyAppData)getApplication()).getTransactions();
                transactionAdapter.notifyDataSetChanged();
                refreshTransactionTotal();
                refreshLayout1.setRefreshing(false);
            }
        });

        product_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                assert transactions.get(i)!=null;
                ((MyAppData)TransactionActivity.this.getApplication()).setTransaction(transactions.get(i));
                // TODO: Add StartActivity call here
            }
        });

        refreshTransactionTotal();

    }

    public void refreshTransactionTotal() {
        if(transactions!=null && transactionTotal!=null){
            String quantity = "Total Transactions : " + String.valueOf(transactions.size());
            transactionTotal.setText(quantity);
        }
    }
}
