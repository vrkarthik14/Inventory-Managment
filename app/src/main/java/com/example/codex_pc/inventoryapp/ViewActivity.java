package com.example.codex_pc.inventoryapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ViewActivity extends AppCompatActivity {

    ListView viewList;
    ArrayList<DynElement> elements;
    DynamicListAdapter adapter;

    Product product;
    Supplier supplier;
    Customer customer;
    Transaction transaction;
    int selection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        viewList = findViewById(R.id.viewList);
        elements = new ArrayList<>();

        selection = ((MyAppData) this.getApplication()).getSelection();

        switch (selection) {
            case 0:
                product = ((MyAppData) this.getApplication()).getProduct();
                elements = createProductView();
                break;

            case 1:
                supplier = ((MyAppData) this.getApplication()).getSupplier();
                break;

            case 2:
                customer = ((MyAppData) this.getApplication()).getCustomer();
                break;

            case 3:
                transaction = ((MyAppData) this.getApplication()).getTransaction();
                break;

            default:
                Log.d("ErrorHandler", "Error occurred in ViewActivity");
        }

        adapter = new DynamicListAdapter(this, elements);
        viewList.setAdapter(adapter);

        viewList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (selection == 0 && i == 8) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(ViewActivity.this);
                    alert.setTitle("Supplier Information");
                    alert.setMessage("Name : " + product.getSupplier().getName() +
                            "\nAddress : " + product.getSupplier().getAddress() +
                            "\nEmail : " + product.getSupplier().getEmail() +
                            "\nMobile No : " + product.getSupplier().getMobileNo() +
                            "\nCompany " + product.getSupplier().getCompany());
                    alert.setPositiveButton("Call", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + product.getSupplier().getMobileNo()));
                            if (ActivityCompat.checkSelfPermission(ViewActivity.this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                // TODO: Consider calling
                                //    ActivityCompat#requestPermissions
                                // here to request the missing permissions, and then overriding
                                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                //                                          int[] grantResults)
                                // to handle the case where the user grants the permission. See the documentation
                                // for ActivityCompat#requestPermissions for more details.
                                return;
                            }
                            startActivity(intent);
                        }
                    });
                    alert.setNegativeButton("Mail", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                                    "mailto",product.getSupplier().getEmail(), null));
                            startActivity(Intent.createChooser(emailIntent, "Send email..."));
                        }
                    });
                    alert.show();
                }

                if(selection==0 && i==9){
                    product.setQuantity(product.getQuantity()+1);
                    Toast.makeText(getApplicationContext(),"Added/Purchased good",Toast.LENGTH_SHORT).show();
                    adapter.notifyDataSetChanged();
                }
                if(selection==0 && i==10){
                    product.setQuantity(product.getQuantity()-1);
                    Toast.makeText(getApplicationContext(),"removed/issued good",Toast.LENGTH_SHORT).show();
                    adapter.notifyDataSetChanged();
                }
            }
        });

    }

    public ArrayList<DynElement> createProductView() {

        ArrayList<DynElement> items = new ArrayList<>();

        DynElement element0 = new DynElement("I");

        DynElement element1 = new DynElement("G");

        DynElement element2 = new DynElement("T");
        element2.setTitle(product.getName());

        DynElement element3 = new DynElement("L");

        DynElement element4 = new DynElement("t");
        element4.setTitle(product.getDesc() + "\n\nProduct ID : " + product.getID());

        DynElement element5 = new DynElement("T");
        element5.setTitle("Price : " + product.getPrice());
        element5.setBgSource(R.drawable.black_text);
        element5.setColorSource(R.color.white);

        DynElement element6 = new DynElement("T");
        element6.setTitle("Quantity : " + product.getQuantity());
        element6.setBgSource(R.drawable.black_text);
        element6.setColorSource(R.color.white);

        DynElement element7 = new DynElement("T");
        element7.setTitle("Condition : " + product.getCondition());
        switch (product.getCondition()) {
            case "No Issues":
                element7.setColorSource(R.color.errorRed);
                break;
            case "Under Repair":
                element7.setColorSource(R.color.okYellow);
                break;
            case "Damaged":
                element7.setColorSource(R.color.goodGreen);
                break;
        }

        DynElement element8 = new DynElement("S");
        element8.setTitle(product.getSupplier().getName());

        DynElement element9 = new DynElement("S");
        element9.setTitle("Add/Purchase");
        element9.setBgSource(R.color.goodGreen);

        DynElement element10 = new DynElement("S");
        element10.setTitle("Sell/Issue");
        element10.setBgSource(R.color.errorRed);

        items.add(element0);
        items.add(element1);
        items.add(element2);
        items.add(element3);
        items.add(element4);
        items.add(element5);
        items.add(element6);
        items.add(element7);
        items.add(element8);
        items.add(element9);
        items.add(element10);

        return items;

    }

}
