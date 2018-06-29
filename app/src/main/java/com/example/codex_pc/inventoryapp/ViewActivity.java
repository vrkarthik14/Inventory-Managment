package com.example.codex_pc.inventoryapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ViewActivity extends AppCompatActivity {


    //Context
    Context context = ViewActivity.this;

    //Layouts
    TableLayout layout;
    LayoutInflater inflater;

    int selection;
    Transaction transaction = new Transaction();

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

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

            case 1: createSupplierView();
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

        elements.add(inflater.inflate(R.layout.dynamicform_imageupload_item,layout,false));
        ImageView imageView = elements.get(i).findViewById(R.id.dynamic_imageupload_item);
        if(product.getImagePath()!=null) {
            // Reference to an image file in Cloud Storage
            StorageReference storageReference = FirebaseStorage.getInstance().getReference().child(product.getImagePath());

            // Download directly from StorageReference using Glide
            // (See MyAppGlideModule for Loader registration)
            Glide.with(context)
                    .load(storageReference)
                    .into(imageView);
        }
        i++;

        elements.add(inflater.inflate(R.layout.dynamic_text_view, layout, false));
        TextView head = elements.get(i).findViewById(R.id.text);
        head.setText("Product Detail ");
        head.setGravity(1);
        head.setTextSize(23);
        i++;

        elements.add(inflater.inflate(R.layout.dynamicform_line_item, layout, false));
        i++;


        elements.add(inflater.inflate(R.layout.dynamic_text_view, layout, false));
        TextView supplier23 = elements.get(i).findViewById(R.id.text);
        supplier23.setText("Name : " +product.getName());
        supplier23.setTextSize(16);
        i++;

        elements.add(inflater.inflate(R.layout.dynamic_text_view, layout, false));
        TextView supplier12 = elements.get(i).findViewById(R.id.text);
        supplier12.setTextSize(16);
        supplier12.setText("Description : " +product.getDesc());
        i++;


        elements.add(inflater.inflate(R.layout.dynamic_dual_textview, layout, false));
        final TextView qty1 = elements.get(i).findViewById(R.id.view_data);
        qty1.setText("Id: "+product.getID());
        TextView prise1= elements.get(i).findViewById(R.id.view_time);
        prise1.setText("Condition: "+product.getCondition());
        i++;

        elements.add(inflater.inflate(R.layout.dynamic_dual_textview, layout, false));
        final TextView qty = elements.get(i).findViewById(R.id.view_data);
        qty.setText("Qty: "+product.getQuantity());
        TextView prise= elements.get(i).findViewById(R.id.view_time);
        prise.setText("Price: "+product.getPrice());
        i++;



        elements.add(inflater.inflate(R.layout.synamic_button_layout, layout, false));
        Button btn1 = elements.get(i).findViewById(R.id.add_pro);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayout layout = new LinearLayout(context);
                layout.setOrientation(LinearLayout.VERTICAL);

                final EditText nameBox = new EditText(context);
                nameBox.setHint("Enter Purchase Qty:");
                layout.addView(nameBox);

                final AlertDialog alert = new AlertDialog.Builder(context)
                        .setTitle("Enter Buying Qty ")
                        .setView(layout)
                        .setPositiveButton("Buy",null)
                        .setNegativeButton("Cancel",null)
                        .create();
                alert.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialogInterface) {
                        Button button = alert.getButton(AlertDialog.BUTTON_POSITIVE);
                        Button button1 = alert.getButton(AlertDialog.BUTTON_NEGATIVE);
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                if(nameBox.getText()==null){
                                    Toast.makeText(getApplicationContext(),"Please fill all the details",Toast.LENGTH_SHORT).show();
                                } else {
                                    int qauntity = Integer.parseInt(nameBox.getText().toString());
                                    ((MyAppData)getApplication()).updateProductQuantity(product.getName(),qauntity,true);
                                    Transaction transaction = new Transaction();
                                    transaction.setProductName(product.getName());
                                    transaction.setProductID(product.getID());
                                    transaction.setDate(getCurrentDate());
                                    transaction.setTime(getCurrentTime());
                                    transaction.setIsSupply(1);
                                    transaction.setSupplier(product.getSupplier());
                                    transaction.setQuantity(qauntity);
                                    transaction.setPrice(product.getPrice());
                                    ((MyAppData)getApplication()).pushTransaction(transaction);
                                    Toast.makeText(context, "Product Purchased successfully", Toast.LENGTH_SHORT).show();
                                    int placeholder = product.getQuantity() + transaction.getQuantity();
                                    String texty = "Qty : " + String.valueOf(placeholder);
                                    qty.setText(texty);
                                    alert.dismiss();
                                }
                            }
                        });
                        button1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                alert.dismiss();
                            }
                        });
                    }
                });
                alert.show();


            }
        });


        Button mail1 = elements.get(i).findViewById(R.id.sell_pro);
        mail1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LinearLayout layout = new LinearLayout(context);
                layout.setOrientation(LinearLayout.VERTICAL);

                final EditText nameBox = new EditText(context);
                nameBox.setHint("Enter Selling Qty:");
                layout.addView(nameBox);

                final Button button = new Button(context);
                button.setText("Choose customer");
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        launchChooserDialog();
                    }
                });

                layout.addView(button);

                final AlertDialog alert = new AlertDialog.Builder(context)
                        .setTitle("Sell The Product:")
                        .setView(layout)
                        .setPositiveButton("Sell",null)
                        .setNegativeButton("Cancel",null)
                        .create();

                alert.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialogInterface) {
                        Button button = alert.getButton(AlertDialog.BUTTON_POSITIVE);
                        Button button1 = alert.getButton(AlertDialog.BUTTON_NEGATIVE);
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                if(nameBox.getText().toString().equals("") || transaction.getCustomer()==null){
                                    Toast.makeText(getApplicationContext(),"Please fill all the details",Toast.LENGTH_SHORT).show();
                                } else {
                                    int qty12= Integer.parseInt(nameBox.getText().toString());
                                    if(qty12 <= product.getQuantity()){
                                        ((MyAppData)getApplication()).updateProductQuantity(product.getName(),qty12 ,false);
                                        transaction.setProductName(product.getName());
                                        transaction.setProductID(product.getID());
                                        transaction.setDate(getCurrentDate());
                                        transaction.setTime(getCurrentTime());
                                        transaction.setIsSupply(0);
                                        transaction.setQuantity(qty12);
                                        transaction.setPrice(product.getPrice());
                                        ((MyAppData)getApplication()).pushTransaction(transaction);
                                        Toast.makeText(context, "Product Sold successfully", Toast.LENGTH_SHORT).show();
                                        int placeholder = product.getQuantity() - transaction.getQuantity();
                                        String texty = "Qty : " + String.valueOf(placeholder);
                                        qty.setText(texty);
                                        alert.dismiss();
                                    }else {
                                        Toast.makeText(ViewActivity.this, "Entered quantity is not available in our shop!Please change qty!", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            }
                        });
                        button1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                alert.dismiss();
                            }
                        });
                    }
                });
                alert.show();





            }
        });
        i++;

        elements.add(inflater.inflate(R.layout.dynamicform_line_item, layout, false));
        i++;
         //
        elements.add(inflater.inflate(R.layout.dynamic_text_view, layout, false));
        TextView head2 = elements.get(i).findViewById(R.id.text);
        head2.setText("Supplier Detail ");
        head2.setGravity(1);
        head2.setTextSize(23);
        i++;

        elements.add(inflater.inflate(R.layout.dynamicform_line_item, layout, false));
        i++;

        elements.add(inflater.inflate(R.layout.dynamic_dual_textview, layout, false));
        TextView supplier = elements.get(i).findViewById(R.id.view_data);
        supplier.setWidth(200);
        supplier.setText("Name : " +product.getSupplier().getName());
        i++;

        elements.add(inflater.inflate(R.layout.dynamic_dual_textview, layout, false));
        TextView sup1 = elements.get(i).findViewById(R.id.view_data);
            sup1.setText("Mobile : " +product.getSupplier().getMobileNo());
        TextView sup2= elements.get(i).findViewById(R.id.view_time);
            sup2.setText("email : " +product.getSupplier().getEmail());
            i++;

        elements.add(inflater.inflate(R.layout.dynamic_dual_textview, layout, false));
        TextView supad = elements.get(i).findViewById(R.id.view_data);
            supad.setText("Address: " +product.getSupplier().getAddress());
        TextView supad1= elements.get(i).findViewById(R.id.view_time);
            supad1.setText("Company: " +product.getSupplier().getCompany());
            i++;

        elements.add(inflater.inflate(R.layout.dynamic_dual_button, layout, false));
        ImageView btn = elements.get(i).findViewById(R.id.call);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;

                    intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + product.getSupplier().getMobileNo()));
               if (ActivityCompat.checkSelfPermission(ViewActivity.this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivity(intent);
            }
        });
        ImageView mail = elements.get(i).findViewById(R.id.mail);
        mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                    Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                            "mailto",product.getSupplier().getEmail(), null));
                    startActivity(Intent.createChooser(emailIntent, "Send email..."));
            }
        });
        i++;

        for (View v : elements){
            layout.addView(v);
        }

    }


    public String getCurrentDate() {

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        return df.format(c);

    }

    public String getCurrentTime() {

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm aa",Locale.ENGLISH);
        return  simpleDateFormat.format(c);

    }


    private void launchChooserDialog() {

        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle("Choose customer");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(context, android.R.layout.select_dialog_singlechoice);
        final ArrayList<Customer> customers = ((MyAppData)ViewActivity.this.getApplication()).getCustomers();
        for (Customer c : customers){
            arrayAdapter.add(c.getName());
        }
        arrayAdapter.add("Add New");

        alert.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alert.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String strName = arrayAdapter.getItem(which);
                assert strName != null;
                if(strName.equals("Add New")){
                    launchSupplierDialog();
                } else {
                    transaction.setCustomer(customers.get(which));
                }
            }
        });
        alert.show();

    }

    private void launchSupplierDialog() {

        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);

        final EditText nameBox = new EditText(context);
        nameBox.setHint("Name");
        layout.addView(nameBox);

        final EditText addressBox = new EditText(context);
        addressBox.setHint("Address");
        layout.addView(addressBox);

        final EditText emailBox = new EditText(context);
        emailBox.setHint("Email");
        layout.addView(emailBox);

        final EditText MobileNoBox = new EditText(context);
        MobileNoBox.setHint("Mobile Number");
        layout.addView(MobileNoBox);

        final EditText CompanyBox = new EditText(context);
        CompanyBox.setHint("Company");
        layout.addView(CompanyBox);

        final AlertDialog alert = new AlertDialog.Builder(context)
                .setTitle("Enter customer Details")
                .setView(layout)
                .setPositiveButton("Create",null)
                .setNegativeButton("Cancel",null)
                .create();

        alert.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button button = alert.getButton(AlertDialog.BUTTON_POSITIVE);
                Button button1 = alert.getButton(AlertDialog.BUTTON_NEGATIVE);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if(nameBox.getText().toString().equals("") || addressBox.getText().toString().equals("") ||
                                MobileNoBox.getText().toString().equals("") || emailBox.getText().toString().equals("") ||
                                CompanyBox.getText().toString().equals("")){
                            Toast.makeText(getApplicationContext(),"Please fill all the details",Toast.LENGTH_SHORT).show();
                        } else {
                            Customer customer = new Customer();
                            customer.setName(nameBox.getText().toString());
                            customer.setAddress(addressBox.getText().toString());
                            customer.setCompany(CompanyBox.getText().toString());
                            customer.setEmail(emailBox.getText().toString());
                            customer.setMobileNo(MobileNoBox.getText().toString());
                            ((MyAppData)ViewActivity.this.getApplication()).pushCustomer(customer);
                            transaction.setCustomer(customer);

                            alert.dismiss();
                        }
                    }
                });
                button1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alert.dismiss();
                    }
                });
            }
        });

        alert.show();

    }


    private void createSupplierOrCustomerView() {

        final Customer customer= ((MyAppData)getApplication()).getCustomer();

        ArrayList<View> elements = new ArrayList<>();
        int i=0;


        elements.add(inflater.inflate(R.layout.dynamic_text_view, layout, false));
        TextView head = elements.get(i).findViewById(R.id.text);
        head.setText("Customer Detail ");
        head.setGravity(1);
        head.setTextSize(30);
        i++;

        elements.add(inflater.inflate(R.layout.dynamicform_line_item, layout, false));
        i++;


        elements.add(inflater.inflate(R.layout.dynamic_text_view, layout, false));
        TextView supplier = elements.get(i).findViewById(R.id.text);
        supplier.setText("Name : " +customer.getName());
        i++;

        elements.add(inflater.inflate(R.layout.dynamic_text_view, layout, false));
        TextView supplier1 = elements.get(i).findViewById(R.id.text);
        supplier1.setText("Address: " +customer.getAddress());
        i++;

        elements.add(inflater.inflate(R.layout.dynamic_text_view, layout, false));
        TextView supplier3 = elements.get(i).findViewById(R.id.text);
        supplier3.setText("Email : " +customer.getEmail());
        i++;

        elements.add(inflater.inflate(R.layout.dynamic_text_view, layout, false));
        TextView supplier5 = elements.get(i).findViewById(R.id.text);
        supplier5.setText("Mobile Number: " +customer.getMobileNo());
        i++;

        elements.add(inflater.inflate(R.layout.dynamic_text_view, layout, false));
        TextView supplier6 = elements.get(i).findViewById(R.id.text);
        supplier6.setText("Company : " +customer.getCompany());
        i++;

        for (View v : elements){
            layout.addView(v);
        }

    }


    private void createSupplierView() {
        final Supplier suppliers= ((MyAppData)getApplication()).getSupplier();

        ArrayList<View> elements = new ArrayList<>();
        int i=0;

        elements.add(inflater.inflate(R.layout.dynamic_text_view, layout, false));
        TextView head = elements.get(i).findViewById(R.id.text);
        head.setText("Suppliers ");
        head.setGravity(1);
        head.setTextSize(30);
        i++;

        elements.add(inflater.inflate(R.layout.dynamicform_line_item, layout, false));
        i++;


        elements.add(inflater.inflate(R.layout.dynamic_text_view, layout, false));
        TextView supplier = elements.get(i).findViewById(R.id.text);
        supplier.setText("Name : " +suppliers.getName());
        i++;

        elements.add(inflater.inflate(R.layout.dynamic_text_view, layout, false));
        TextView supplier1 = elements.get(i).findViewById(R.id.text);
        supplier1.setText("Address: " +suppliers.getAddress());
        i++;

        elements.add(inflater.inflate(R.layout.dynamic_text_view, layout, false));
        TextView supplier3 = elements.get(i).findViewById(R.id.text);
        supplier3.setText("Email : " +suppliers.getEmail());
        i++;

        elements.add(inflater.inflate(R.layout.dynamic_text_view, layout, false));
        TextView supplier5 = elements.get(i).findViewById(R.id.text);
        supplier5.setText("Mobile Number: " +suppliers.getMobileNo());
        i++;

        elements.add(inflater.inflate(R.layout.dynamic_text_view, layout, false));
        TextView supplier6 = elements.get(i).findViewById(R.id.text);
        supplier6.setText("Company : " +suppliers.getCompany());
        i++;

        for (View v : elements){
            layout.addView(v);
        }


    }




}
