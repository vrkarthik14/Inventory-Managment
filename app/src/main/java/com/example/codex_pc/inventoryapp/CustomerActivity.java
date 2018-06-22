package com.example.codex_pc.inventoryapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CustomerActivity extends AppCompatActivity {

    ArrayList<Customer> customers;
    CustomerAdapter customerAdapter;
    TextView customerTotal;

    @Override
    protected void onStart() {
        super.onStart();

        if (customers!=null && customerAdapter!=null) {
            customers = ((MyAppData)CustomerActivity.this.getApplication()).getCustomers();
            customerAdapter.notifyDataSetChanged();
            refreshCustomerCount();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

        Toast.makeText(getApplicationContext(), "Swipe to refresh and load data", Toast.LENGTH_SHORT).show();

        final SwipeRefreshLayout refreshLayout3 = findViewById(R.id.swiperefresh1);
        customerTotal = findViewById(R.id.customer_count_total);

        customers = ((MyAppData)this.getApplication()).getCustomers();

        //Log.i("Check",products.get(0).getName());
        customerAdapter = new CustomerAdapter(this,customers);
        ListView product_list_view = findViewById(R.id.customer_list);
        product_list_view.setAdapter(customerAdapter);

        FloatingActionButton addCustomers = findViewById(R.id.add_customers);
        addCustomers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CustomerActivity.this, FormActivity.class));
            }
        });

        refreshLayout3.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                customers= ((MyAppData)getApplication()).getCustomers();
                customerAdapter.notifyDataSetChanged();
                refreshCustomerCount();
                refreshLayout3.setRefreshing(false);
            }
        });

        product_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                assert customers.get(i)!=null;
                ((MyAppData)CustomerActivity.this.getApplication()).setCustomer(customers.get(i));
                // TODO: Add StartActivity call here
            }
        });

        refreshCustomerCount();

    }

    public void refreshCustomerCount() {
        if(customers!=null && customerTotal!=null){
            String quantity = "Total Customers : " + String.valueOf(customers.size());
            customerTotal.setText(quantity);
        }
    }
}
