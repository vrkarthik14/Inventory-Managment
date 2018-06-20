package com.example.codex_pc.inventoryapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class FormActivity extends AppCompatActivity {

    ListView mainList;
    ArrayList<DynElement> elements;
    DynamicListAdapter adapter;

    int selection;
    private final static int RESULT_LOAD_IMG = 1;

    Supplier supplier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        mainList = findViewById(R.id.mainList);

        selection = ((MyAppData)this.getApplication()).getSelection();

        elements = new ArrayList<>();

        if (selection==0) {elements = createProductForm();}
        else if(selection==1) { elements = createSupplierOrCustomerForm("Supplier"); }
        else if (selection==2) { elements = createSupplierOrCustomerForm("Customer"); }

        adapter = new DynamicListAdapter(this, elements);
        mainList.setItemsCanFocus(true);
        mainList.setAdapter(adapter);

        scrollMyListViewToBottom();

        mainList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(selection==0 && i==0) {
                    // Create intent to Open Image applications like Gallery, Google Photos
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    // Start the Intent
                    startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
                }
                if(selection==0 && i==10){

                    final String[] items = {"No Issues","Under Repair","Damaged"};

                    AlertDialog.Builder alert = new AlertDialog.Builder(FormActivity.this);
                    alert.setTitle("Choose Condition");
                    alert.setItems(items, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int item) {
                            // Do something with the selection
                            elements.get(10).setResult1(items[item]);
                            dialog.dismiss();
                        }
                    });
                    alert.show();
                }
                if(selection==0 && i==elements.size()-1){
                    launchChooserDialog();
                }
            }
        });

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);

        if(requestCode==RESULT_LOAD_IMG){
            if(resultCode == RESULT_OK){
                Uri selectedImage = data.getData();
                elements.get(0).setImageURI(selectedImage);
                adapter.notifyDataSetChanged();
                String path = "inventory/" + UUID.randomUUID() + ".jpg";
                StorageReference ref = FirebaseStorage.getInstance().getReference().child(path);
                elements.get(0).setResult1(path);
            }
        }

    }

    public ArrayList<DynElement> createProductForm() {

        Log.d("ListHandler","Fired");

        ArrayList<DynElement> elements = new ArrayList<>();

        DynElement element0 = new DynElement("I");

        DynElement element1 = new DynElement("G");

        DynElement element2 = new DynElement("T");
        element2.setTitle("Please enter Product Information");

        DynElement element3 = new DynElement("L");

        DynElement element4 = new DynElement("E");
        element4.setTitle("Product Name");
        element4.setInputTypeing(InputType.TYPE_CLASS_TEXT);

        DynElement element5 = new DynElement("E");
        element5.setTitle("Product Price");
        element5.setInputTypeing(InputType.TYPE_CLASS_NUMBER);

        DynElement element6 = new DynElement("E");
        element6.setTitle("Product Quantity");
        element6.setInputTypeing(InputType.TYPE_CLASS_NUMBER);

        DynElement element7 = new DynElement("E");
        element7.setTitle("Product Description");
        element7.setInputTypeing(InputType.TYPE_TEXT_FLAG_MULTI_LINE);

        DynElement element8 = new DynElement("E");
        element8.setTitle("Product ID");
        element8.setInputTypeing(InputType.TYPE_CLASS_NUMBER);

        DynElement element9 = new DynElement("t");
        element9.setTitle("Note. long press here to open the barcode scanner");

        DynElement element10 = new DynElement("S");
        element10.setTitle("Choose Item Condition");

        DynElement element11 = new DynElement("S");
        element11.setTitle("Enter Supplier Details");

        elements.add(element0);
        elements.add(element1);
        elements.add(element2);
        elements.add(element3);
        elements.add(element4);
        elements.add(element5);
        elements.add(element6);
        elements.add(element7);
        elements.add(element8);
        elements.add(element9);
        elements.add(element10);
        elements.add(element11);

        return elements;
    }

    public ArrayList<DynElement> createSupplierOrCustomerForm(String s) {
        ArrayList<DynElement> elements = new ArrayList<>();

        DynElement element0 = new DynElement("G");
        element0.setGapSize(8);

        DynElement dash = new DynElement("L");

        DynElement element = new DynElement("T");
        element.setTitle("Enter " + s +" Information");

        DynElement element1 = new DynElement("E");
        element1.setTitle("Name");
        element1.setInputTypeing(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);

        DynElement element2 = new DynElement("E");
        element2.setTitle("Address");
        element2.setInputTypeing(InputType.TYPE_CLASS_TEXT);

        DynElement element3 = new DynElement("E");
        element3.setTitle("Email");
        element3.setInputTypeing(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);

        DynElement element4 = new DynElement("E");
        element4.setTitle("Mobile No");
        element4.setInputTypeing(InputType.TYPE_CLASS_PHONE);

        DynElement element5 = new DynElement("E");
        element5.setTitle(s + " Company");
        element5.setInputTypeing(InputType.TYPE_CLASS_TEXT);

        elements.add(element0);
        elements.add(element);
        elements.add(dash);
        elements.add(element1);
        elements.add(element2);
        elements.add(element3);
        elements.add(element4);
        elements.add(element5);

        return elements;

    }

    public void Submit(View view) {

        Boolean valid = true;

        for (int i=0;i<elements.size();i++) {
            if(!elements.get(i).getType().equals("G") && !elements.get(i).getType().equals("L")
                    && !elements.get(i).getType().equals("T") && !elements.get(i).getType().equals("t")){
                if(elements.get(i).getResult1().equals("")){
                    valid = false;
                    Log.d("ErrorHandler","It becomes false in " + String.valueOf(i));
                }
            }
        }

        if(selection==0 &&supplier==null){
            valid = false;
        }

         if(selection==0 && valid){

            Product product = new Product();

            product.setName(elements.get(4).getResult1());
            product.setPrice(Integer.parseInt(elements.get(5).getResult1()));
            product.setQuantity(Integer.parseInt(elements.get(6).getResult1()));
            product.setDesc(elements.get(7).getResult1());
            product.setID(elements.get(8).getResult1());
            product.setCondition(elements.get(9).getResult1());
            product.setSupplier(supplier);
            product.setImagePath(elements.get(0).getImagePath());

            ((MyAppData) this.getApplication()).pushProduct(product);

             Transaction transaction = new Transaction();
             transaction.setProductName(product.getName());
             transaction.setDate(getCurrentDate());
             transaction.setTime(getCurrentTime());
             transaction.setIsSupply(1);
             transaction.setSupplier(supplier);
             transaction.setQuantity(product.getQuantity());
             transaction.setPrice(product.getPrice());
             ((MyAppData)this.getApplication()).pushTransaction(transaction);

         } else if(selection==1 && valid) {

            Supplier supplier = new Supplier();
            supplier.setName(elements.get(3).getResult1());
            supplier.setAddress(elements.get(4).getResult1());
            supplier.setEmail(elements.get(5).getResult1());
            supplier.setMobileNo(elements.get(6).getResult1());
            supplier.setCompany(elements.get(7).getResult1());
            ((MyAppData)this.getApplication()).pushSupllier(supplier);

         } else if(selection==2 && valid) {

             Customer customer = new Customer();
             customer.setName(elements.get(3).getResult1());
             customer.setAddress(elements.get(4).getResult1());
             customer.setEmail(elements.get(5).getResult1());
             customer.setMobileNo(elements.get(6).getResult1());
             customer.setCompany(elements.get(7).getResult1());
             ((MyAppData)this.getApplication()).pushCustomer(customer);

         } else {

            Toast.makeText(getApplicationContext(),"Please Fill All forms",Toast.LENGTH_SHORT).show();

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

    private void scrollMyListViewToBottom() {
        mainList.post(new Runnable() {
            @Override
            public void run() {
                // Select the last row so it will scroll into view...
                mainList.setSelection(adapter.getCount() - 1);
            }
        });
    }

    private void launchChooserDialog() {

        AlertDialog.Builder alert = new AlertDialog.Builder(FormActivity.this);
        alert.setTitle("Choose Supplier");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(FormActivity.this, android.R.layout.select_dialog_singlechoice);
        final ArrayList<Supplier> suppliers = ((MyAppData)FormActivity.this.getApplication()).getSuppliers();
        for (int i=0;i<suppliers.size();i++){
            arrayAdapter.add(suppliers.get(i).getName());
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
                    supplier = suppliers.get(which);
                    elements.get(11).setResult1(supplier.getName());
                }
            }
        });
        alert.show();

    }

    private void launchSupplierDialog() {

        LinearLayout layout = new LinearLayout(FormActivity.this);
        layout.setOrientation(LinearLayout.VERTICAL);

        final EditText nameBox = new EditText(FormActivity.this);
        nameBox.setHint("Name");
        layout.addView(nameBox);

        final EditText addressBox = new EditText(FormActivity.this);
        addressBox.setHint("Address");
        layout.addView(addressBox);

        final EditText emailBox = new EditText(FormActivity.this);
        emailBox.setHint("Email");
        layout.addView(emailBox);

        final EditText MobileNoBox = new EditText(FormActivity.this);
        MobileNoBox.setHint("Mobile Number");
        layout.addView(MobileNoBox);

        final EditText CompanyBox = new EditText(FormActivity.this);
        CompanyBox.setHint("Company");
        layout.addView(CompanyBox);

        final AlertDialog alert = new AlertDialog.Builder(FormActivity.this)
                .setTitle("Enter Supplier Details")
                .setView(layout)
                .setPositiveButton("Create",null)
                .setNegativeButton("Cancel",null)
                .create();

        alert.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button button = ((AlertDialog) alert).getButton(AlertDialog.BUTTON_POSITIVE);
                Button button1 = ((AlertDialog) alert).getButton(AlertDialog.BUTTON_NEGATIVE);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if(nameBox.getText().toString().equals("") || addressBox.getText().toString().equals("") ||
                                MobileNoBox.getText().toString().equals("") || emailBox.getText().toString().equals("") ||
                                CompanyBox.getText().toString().equals("")){
                            Toast.makeText(getApplicationContext(),"Please fill all the details",Toast.LENGTH_SHORT).show();
                        } else {
                            supplier = new Supplier();
                            supplier.setName(nameBox.getText().toString());
                            supplier.setAddress(addressBox.getText().toString());
                            supplier.setCompany(CompanyBox.getText().toString());
                            supplier.setEmail(emailBox.getText().toString());
                            supplier.setMobileNo(MobileNoBox.getText().toString());
                            elements.get(11).setResult1(supplier.getName());

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

}
