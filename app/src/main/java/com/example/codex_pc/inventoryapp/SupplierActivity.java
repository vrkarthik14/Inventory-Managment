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

public class SupplierActivity extends AppCompatActivity {

    ArrayList<Supplier> suppliers;
    SupplierAdapter supplierAdapter;
    TextView supplierTotal;

    @Override
    protected void onStart() {
        super.onStart();

        if (suppliers!=null && supplierAdapter!=null) {
            suppliers = ((MyAppData)SupplierActivity.this.getApplication()).getSuppliers();
            supplierAdapter.notifyDataSetChanged();
            refreshSupplierCount();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplier);

        Toast.makeText(getApplicationContext(), "Swipe to refresh and load data", Toast.LENGTH_SHORT).show();

        final SwipeRefreshLayout refreshLayout2 = findViewById(R.id.swiperefresh2);
        supplierTotal = findViewById(R.id.supplier_count_total);

        suppliers = ((MyAppData)this.getApplication()).getSuppliers();

        //Log.i("Check",products.get(0).getName());
         supplierAdapter = new SupplierAdapter(this,suppliers);
        ListView product_list_view = findViewById(R.id.supplier_list);
        product_list_view.setAdapter(supplierAdapter);

        FloatingActionButton addSuppliers = findViewById(R.id.add_suppliers);
        addSuppliers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SupplierActivity.this, FormActivity.class));
            }
        });

        refreshLayout2.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                suppliers= ((MyAppData)getApplication()).getSuppliers();
                supplierAdapter.notifyDataSetChanged();
                refreshSupplierCount();
                refreshLayout2.setRefreshing(false);
            }
        });

        product_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                assert suppliers.get(i)!=null;
                ((MyAppData)SupplierActivity.this.getApplication()).setSupplier(suppliers.get(i));
                // TODO: Add StartActivity call here
                startActivity(new Intent(SupplierActivity.this,ViewActivity.class));
            }
        });

        refreshSupplierCount();

    }

    public void refreshSupplierCount() {

        if (supplierTotal!=null && suppliers!=null) {
            String quantity = "Total Suppliers : " + String.valueOf(suppliers.size());
            supplierTotal.setText(quantity);
        }

    }

}
