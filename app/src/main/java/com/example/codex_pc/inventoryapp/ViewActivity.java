package com.example.codex_pc.inventoryapp;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class ViewActivity extends AppCompatActivity {


    //Context
    Context context = ViewActivity.this;

    //Layouts
    TableLayout layout;
    LayoutInflater inflater;

    int selection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        layout = findViewById(R.id.dynamic_view_layout);
        inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        selection = ((MyAppData)this.getApplication()).getSelection();

        setLayoutBasedOnSelection();


    }

    private void setLayoutBasedOnSelection() {

        switch (selection) {
            case 0: createProductView();
                break;

            case 1: createSupplierOrCustomeView();
                break;

            case 2: createSupplierOrCustomerView();
                break;
            case 3: createTransactionView();
                break;

            default:
                Log.d("ErrorHandler","Error choosing form");
        }

    }

    private void createTransactionView() {

        final Transaction transaction = ((MyAppData)getApplication()).getTransaction();

        ArrayList<View> elements = new ArrayList<>();
        int i=0;

        elements.add(inflater.inflate(R.layout.dynamic_text_view, layout, false));
        TextView head = elements.get(i).findViewById(R.id.text);
        head.setText("Product Detail ");
        head.setGravity(1);
        head.setTextSize(30);
        i++;

        elements.add(inflater.inflate(R.layout.dynamicform_line_item, layout, false));
        i++;

        elements.add(inflater.inflate(R.layout.dynamic_text_view, layout, false));
        TextView tv = elements.get(i).findViewById(R.id.text);
        tv.setText("Product Name: "+transaction.getProductName());
        i++;

        elements.add(inflater.inflate(R.layout.dynamic_text_view, layout, false));
        TextView id = elements.get(i).findViewById(R.id.text);
        id.setText("Product Id: "+transaction.getProductID());
        i++;

        elements.add(inflater.inflate(R.layout.dynamic_dual_textview, layout, false));
        TextView date = elements.get(i).findViewById(R.id.view_data);
        date.setText("Date: "+transaction.getDate());
        TextView time = elements.get(i).findViewById(R.id.view_time);
        time.setText("Time: "+transaction.getTime());
        i++;


        elements.add(inflater.inflate(R.layout.dynamic_dual_textview, layout, false));
        TextView qty = elements.get(i).findViewById(R.id.view_data);
        qty.setText("Qty: "+transaction.getQuantity());
        TextView prise= elements.get(i).findViewById(R.id.view_time);
        prise.setText("Price: "+transaction.getPrice());
        i++;


        elements.add(inflater.inflate(R.layout.dynamic_text_view, layout, false));
        TextView head2 = elements.get(i).findViewById(R.id.text);
        head2.setText("Supplier/Customer Detail ");
        head2.setGravity(1);
        head2.setTextSize(30);
        i++;

        elements.add(inflater.inflate(R.layout.dynamicform_line_item, layout, false));
        i++;

        elements.add(inflater.inflate(R.layout.dynamic_dual_textview, layout, false));
        TextView supplier = elements.get(i).findViewById(R.id.view_data);
        supplier.setWidth(200);
        if(transaction.getIsSupply()==1)
        supplier.setText("Name : " +transaction.getSupplier().getName());
        else 
            supplier.setText("Name : "+transaction.getCustomer().getName());
        i++;


        elements.add(inflater.inflate(R.layout.dynamic_dual_textview, layout, false));
        TextView sup1 = elements.get(i).findViewById(R.id.view_data);
        if(transaction.getIsSupply()==1)
            sup1.setText("Mobile : " +transaction.getSupplier().getMobileNo());
        else
            sup1.setText("Mobile : "+transaction.getCustomer().getMobileNo());
        TextView sup2= elements.get(i).findViewById(R.id.view_time);

        if(transaction.getIsSupply()==1)
            sup2.setText("email : " +transaction.getSupplier().getEmail());
        else
            sup2.setText("email : "+transaction.getCustomer().getEmail());
        i++;

        elements.add(inflater.inflate(R.layout.dynamic_dual_textview, layout, false));
        TextView supad = elements.get(i).findViewById(R.id.view_data);
        if(transaction.getIsSupply()==1)
            supad.setText("Address: " +transaction.getSupplier().getAddress());
        else
            supad.setText("Address: "+transaction.getCustomer().getAddress());
        TextView supad1= elements.get(i).findViewById(R.id.view_time);

        if(transaction.getIsSupply()==1)
            supad1.setText("Company: " +transaction.getSupplier().getCompany());
        else
            supad1.setText("Oompany: "+transaction.getCustomer().getCompany());
        i++;

        elements.add(inflater.inflate(R.layout.dynamic_dual_button, layout, false));
        ImageView btn = elements.get(i).findViewById(R.id.call);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                if(transaction.getIsSupply()==1){
                    intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + transaction.getSupplier().getMobileNo()));
                }else {

                    intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + transaction.getCustomer().getMobileNo()));

                }if (ActivityCompat.checkSelfPermission(ViewActivity.this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivity(intent);
            }
        });
        ImageView mail = elements.get(i).findViewById(R.id.mail);
        mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(transaction.getIsSupply()==1){

                    Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                            "mailto",transaction.getSupplier().getEmail(), null));
                    startActivity(Intent.createChooser(emailIntent, "Send email..."));

                }else {

                    Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                            "mailto",transaction.getCustomer().getEmail(), null));
                    startActivity(Intent.createChooser(emailIntent, "Send email..."));

                }
            }
        });
        i++;

        for (View v : elements){
            layout.addView(v);
        }
    }

    private void createProductView() {

        final Product product= ((MyAppData)getApplication()).getProduct();

        ArrayList<View> elements = new ArrayList<>();
        int i=0;
    }


    private void createSupplierOrCustomerView() {
    }

    private void createSupplierOrCustomeView() {
    }


}
