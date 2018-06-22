package com.example.codex_pc.inventoryapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ProductActivity extends AppCompatActivity {

    ArrayList<Product> products;
    ProductAdapter productAdapter;
    TextView productTotal;

    @Override
    protected void onStart() {
        super.onStart();

        if (products!=null && productAdapter!=null) {
            products = ((MyAppData)ProductActivity.this.getApplication()).getProducts();
            productAdapter.notifyDataSetChanged();
            refreshProductCount();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        Toast.makeText(getApplicationContext(), "Swipe to refresh and load data", Toast.LENGTH_SHORT).show();

        Log.d("ActivityHandler","OnCreate");

        final SwipeRefreshLayout refreshLayout = findViewById(R.id.swiperefresh);
        productTotal = findViewById(R.id.product_count_total);

        products = ((MyAppData)this.getApplication()).getProducts();

        //Log.i("Check",products.get(0).getName());
        productAdapter = new ProductAdapter(this,products);
        ListView product_list_view = findViewById(R.id.product_list);
        product_list_view.setAdapter(productAdapter);
        FloatingActionButton addProducts = findViewById(R.id.add_products);
        addProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProductActivity.this, FormActivity.class));
            }
        });

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                products = ((MyAppData)getApplication()).getProducts();
                productAdapter.notifyDataSetChanged();
                refreshProductCount();
                refreshLayout.setRefreshing(false);
            }
        });

        product_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                assert products.get(i)!=null;
                ((MyAppData)ProductActivity.this.getApplication()).setProduct(products.get(i));
                //TODO: Add startActivity call here
            }
        });

        refreshProductCount();

    }

    public void refreshProductCount() {

        if(products!=null && productTotal!=null){
            int q = 0;
            for (Product p : products){
                q += p.getQuantity();
            }
            String quantity = "Product Total : " + String.valueOf(q);
            productTotal.setText(quantity);
        }

    }

}
