package com.example.codex_pc.inventoryapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class SupplierActicity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplier);

        ArrayList<Supplier> suppliers= ((MyAppData)this.getApplication()).getSuppliers();
        //Log.i("Check",products.get(0).getName());
        SupplierAdapter supplierAdapter = new SupplierAdapter(this,suppliers);
        ListView product_list_view = findViewById(R.id.supplier_list);
        product_list_view.setAdapter(supplierAdapter);


    }


}
