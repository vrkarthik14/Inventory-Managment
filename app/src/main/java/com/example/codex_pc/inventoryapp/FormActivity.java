package com.example.codex_pc.inventoryapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
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

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class FormActivity extends AppCompatActivity {

    //Constants
    private final static int RESULT_LOAD_IMG = 123;

    //Context
    Context context = FormActivity.this;

    //Layouts
    TableLayout layout;
    LayoutInflater inflater;

    //Views
    Button  submitButton;                                                 // Common
    ImageView image;                                                      // Product
    TextInputLayout titleBox,descBox,priceBox,quantityBox,idBox;          //
    EditText nameET ,descET, priceET, quantityET, idET;                   //
    Button conditionButton, supplierButton;                               //
    TextInputLayout nameBox,addressBox,emailBox, mobilenoBox, companyBox; // Supplier [or] Customer
    EditText name, address, email, mobileno, company;                     //

    //Results
    String imagePath = "";    //Product
    String condition = "";    //Supplier [or] Customer
    Supplier supplier = null; //

    //Controllers
    int selection;
    Uri selectedImage;
    MaterialDialog dialog;
    ArrayList<String> names;

    @Override
    protected void onStart() {
        super.onStart();

        if (idET != null) {

            String[] scannedRes = ((MyAppData)FormActivity.this.getApplication()).getScannedID().split("/");

            if(scannedRes[0].equals("true")) {
                idET.setText(scannedRes[1]);
            }
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        layout = findViewById(R.id.dynamicLayout);
        inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        names = new ArrayList<>();

        selection = ((MyAppData)this.getApplication()).getSelection();

        setLayoutBasedOnSelection();

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);

        if(requestCode==RESULT_LOAD_IMG){
            if(resultCode == RESULT_OK){
                Uri selectedImage = data.getData();
                assert selectedImage!=null;
                image.setImageURI(selectedImage);

                imagePath = "inventory/" + UUID.randomUUID().toString() + ".jpg";
                this.selectedImage = selectedImage;

            }
        }

    }

    //==============================================================================================
    //==============================================================================================
    // Upload Image Function

    public void uploadImageAndCloseActivity(final Product product,final Transaction transaction) {

        launchProgressDialog();

        StorageReference ref = FirebaseStorage.getInstance().getReference().child(imagePath);

        ref.putFile(selectedImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                ((MyAppData) FormActivity.this.getApplication()).pushProduct(product);
                ((MyAppData)FormActivity.this.getApplication()).pushTransaction(transaction);
                dismissDialog();
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dismissDialog();
                Log.d("ErrorHandler","Error uploading image");
                Toast.makeText(getApplicationContext(),"Error Uploading Image",Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }

    //==============================================================================================
    //==============================================================================================
    // Switch Cases

    public void setLayoutBasedOnSelection() {

        switch (selection) {
            case 0: createProductForm();
                ArrayList<Product> products = ((MyAppData)FormActivity.this.getApplication()).getProducts();
                for (Product p : products){
                    names.add(p.getName());
                }
                break;

            case 1: createSupplierOrCustomerForm("Supplier");
                ArrayList<Supplier> suppliers = ((MyAppData)FormActivity.this.getApplication()).getSuppliers();
                for (Supplier s : suppliers){
                    names.add(s.getName());
                }
                break;

            case 2: createSupplierOrCustomerForm("Customer");
                ArrayList<Customer> customers = ((MyAppData)FormActivity.this.getApplication()).getCustomers();
                for (Customer c : customers){
                    names.add(c.getName());
                }
                break;

            default:
                Log.d("ErrorHandler","Error choosing form");
        }

    }

    //==============================================================================================
    //==============================================================================================
    // All the form creation functions are here

    @SuppressLint("CutPasteId")
    public void createProductForm() {

        //==========================================================================================
        //Declaration and creation of views to be populated

        ArrayList<View> elements = new ArrayList<>();
        int i=0;

        elements.add(inflater.inflate(R.layout.dynamicform_imageupload_item, layout, false));
        image = elements.get(i).findViewById(R.id.dynamic_imageupload_item);
        i++;

        elements.add(inflater.inflate(R.layout.dynamicform_title_item, layout, false));
        TextView tv = elements.get(i).findViewById(R.id.dynamic_title_item);
        tv.setText("Enter new Product Details");
        i++;

        elements.add(inflater.inflate(R.layout.dynamicform_line_item, layout, false));
        i++;

        elements.add(inflater.inflate(R.layout.dynamicform_edittext_item, layout, false));
        titleBox = elements.get(i).findViewById(R.id.dynamic_edittext_item);
        nameET = elements.get(i).findViewById(R.id.et);
        titleBox.setHint("Name");
        i++;

        elements.add(inflater.inflate(R.layout.dynamicform_edittext_item, layout, false));
        descBox = elements.get(i).findViewById(R.id.dynamic_edittext_item);
        descET = elements.get(i).findViewById(R.id.et);
        descET.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        descBox.setHint("Description");
        i++;

        elements.add(inflater.inflate(R.layout.dynamicform_dualedittexts_item, layout, false));
        priceBox = elements.get(i).findViewById(R.id.dynamic_edittext_item_1);
        priceET = elements.get(i).findViewById(R.id.et1);
        priceET.setInputType(InputType.TYPE_CLASS_NUMBER);
        priceBox.setHint("Price");
        quantityBox = elements.get(i).findViewById(R.id.dynamic_edittext_item_2);
        quantityET = elements.get(i).findViewById(R.id.et2);
        quantityET.setInputType(InputType.TYPE_CLASS_NUMBER);
        quantityBox.setHint("Quantity");
        i++;

        elements.add(inflater.inflate(R.layout.dynamicform_edittext_item, layout, false));
        idBox = elements.get(i).findViewById(R.id.dynamic_edittext_item);
        idET = elements.get(i).findViewById(R.id.et);
        idET.setInputType(InputType.TYPE_CLASS_NUMBER);
        idBox.setHint("Product ID");
        i++;

        elements.add(inflater.inflate(R.layout.dynamicform_subtext_item, layout, false));
        TextView tv1 = elements.get(i).findViewById(R.id.dynamic_subtext_item);
        tv1.setText("Note.long press here to open the barcode scanner");
        i++;

        elements.add(inflater.inflate(R.layout.dynamicform_button_item, layout, false));
        conditionButton = elements.get(i).findViewById(R.id.dynamic_button_item);
        conditionButton.setText("Choose Item Condition");
        i++;

        elements.add(inflater.inflate(R.layout.dynamicform_button_item, layout, false));
        supplierButton = elements.get(i).findViewById(R.id.dynamic_button_item);
        supplierButton.setText("Enter Supplier Details");
        i++;

        elements.add(inflater.inflate(R.layout.dynamicform_button_item, layout, false));
        submitButton = elements.get(i).findViewById(R.id.dynamic_button_item);
        submitButton.setText("Submit");

        //==========================================================================================
        //Add listeners for any of the items here

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
                getIntent.setType("image/*");

                Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                pickIntent.setType("image/*");

                Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});

                startActivityForResult(chooserIntent, RESULT_LOAD_IMG);
            }
        });

        conditionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String[] items = {"No Issues","Under Repair","Damaged"};

                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                alert.setTitle("Choose Condition");
                alert.setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        // Do something with the selection
                        condition = items[item];
                        dialog.dismiss();
                    }
                });
                alert.show();
            }
        });

        tv1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                startActivity(new Intent(context,SimpleScannerActivity.class));
                return false;
            }
        });

        supplierButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchChooserDialog();
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!imagePath.equals("") && !nameET.getText().toString().equals("") &&
                        !priceET.getText().toString().equals("") && !quantityET.getText().toString().equals("") &&
                        !descET.getText().toString().equals("") && !idET.getText().toString().equals("") &&
                        !condition.equals("") && supplier!=null) {

                    if (!names.contains(nameET.getText().toString())) {

                        Product product = new Product();

                        product.setName(nameET.getText().toString());
                        product.setPrice(Integer.parseInt(priceET.getText().toString()));
                        product.setQuantity(Integer.parseInt(quantityET.getText().toString()));
                        product.setDesc(descET.getText().toString());
                        product.setID(idET.getText().toString());
                        product.setCondition(condition);
                        product.setSupplier(supplier);
                        product.setImagePath(imagePath);

                        Transaction transaction = new Transaction();
                        transaction.setProductName(product.getName());
                        transaction.setProductID(product.getID());
                        transaction.setDate(getCurrentDate());
                        transaction.setTime(getCurrentTime());
                        transaction.setIsSupply(1);
                        transaction.setSupplier(supplier);
                        transaction.setQuantity(product.getQuantity());
                        transaction.setPrice(product.getPrice());

                        uploadImageAndCloseActivity(product, transaction);

                    } else {

                        Toast.makeText(getApplicationContext(), "This product name already exists", Toast.LENGTH_SHORT).show();

                    }

                } else {
                    Toast.makeText(getApplicationContext(),"Please fill out all the inputs",Toast.LENGTH_SHORT).show();
                }
            }
        });

        //==========================================================================================
        //Adding of views to the layout

        /* layout.addView(element0);
        layout.addView(element0_5);
        layout.addView(dash);
        layout.addView(element1);
        layout.addView(element2);
        layout.addView(element3);
        layout.addView(element4);
        layout.addView(element5);
        layout.addView(element6);
        layout.addView(element7);
        layout.addView(element8); */

        for (View v : elements){
            layout.addView(v);
        }

    }

    @SuppressLint("CutPasteId")
    public void createSupplierOrCustomerForm(String s){

        //==========================================================================================
        //Declaration and creation of view to be populated

        ArrayList<View> elements = new ArrayList<>();
        int i = 0;

        elements.add(inflater.inflate(R.layout.dynamicform_title_item,layout,false));
        TextView tv1 = elements.get(i).findViewById(R.id.dynamic_title_item);
        tv1.setText("Enter " + s + " Details");
        i++;

        elements.add(inflater.inflate(R.layout.dynamicform_line_item,layout,false));
        i++;

        elements.add(inflater.inflate(R.layout.dynamicform_edittext_item, layout, false));
        nameBox = elements.get(i).findViewById(R.id.dynamic_edittext_item);
        name = elements.get(i).findViewById(R.id.et);
        nameBox.setHint("Name");
        name.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
        i++;

        elements.add(inflater.inflate(R.layout.dynamicform_edittext_item, layout, false));
        addressBox = elements.get(i).findViewById(R.id.dynamic_edittext_item);
        address = elements.get(i).findViewById(R.id.et);
        addressBox.setHint("Address");
        address.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
        i++;

        elements.add(inflater.inflate(R.layout.dynamicform_edittext_item, layout, false));
        emailBox = elements.get(i).findViewById(R.id.dynamic_edittext_item);
        email = elements.get(i).findViewById(R.id.et);
        emailBox.setHint("Email");
        email.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
        i++;

        elements.add(inflater.inflate(R.layout.dynamicform_edittext_item, layout, false));
        mobilenoBox = elements.get(i).findViewById(R.id.dynamic_edittext_item);
        mobileno = elements.get(i).findViewById(R.id.et);
        mobilenoBox.setHint("Mobile Number");
        mobileno.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
        i++;

        elements.add(inflater.inflate(R.layout.dynamicform_edittext_item, layout, false));
        companyBox = elements.get(i).findViewById(R.id.dynamic_edittext_item);
        company = elements.get(i).findViewById(R.id.et);
        companyBox.setHint("Supplier Company");
        company.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
        i++;

        elements.add(inflater.inflate(R.layout.dynamicform_button_item, layout, false));
        submitButton = elements.get(i).findViewById(R.id.dynamic_button_item);
        submitButton.setText("Submit");

        //==========================================================================================
        //Add listeners for any of the items here

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!name.getText().toString().equals("") && !address.getText().toString().equals("") &&
                        !mobileno.getText().toString().equals("") && !email.getText().toString().equals("") &&
                        !company.getText().toString().equals("")) {

                    if(!names.contains(name.getText().toString())) {

                        if (selection == 1) {

                            Supplier supplier = new Supplier();
                            supplier.setName(name.getText().toString());
                            supplier.setAddress(address.getText().toString());
                            supplier.setEmail(email.getText().toString());
                            supplier.setMobileNo(mobileno.getText().toString());
                            supplier.setCompany(company.getText().toString());
                            ((MyAppData) FormActivity.this.getApplication()).pushSupllier(supplier);

                        } else {

                            Customer customer = new Customer();
                            customer.setName(name.getText().toString());
                            customer.setAddress(address.getText().toString());
                            customer.setEmail(email.getText().toString());
                            customer.setMobileNo(mobileno.getText().toString());
                            customer.setCompany(company.getText().toString());
                            ((MyAppData) FormActivity.this.getApplication()).pushCustomer(customer);

                        }

                        finish();

                    } else {

                        Toast.makeText(getApplicationContext(), "Please choose another name. This is taken", Toast.LENGTH_SHORT).show();

                    }

                } else {

                    Toast.makeText(getApplicationContext(),"Please fill out all the inputs",Toast.LENGTH_SHORT).show();

                }

            }
        });

        //==========================================================================================
        //Adding of views to the layout

        for (View v : elements){
            layout.addView(v);
        }

    }

    //==============================================================================================
    //==============================================================================================
    // Helper Functions

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
        alert.setTitle("Choose Supplier");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(context, android.R.layout.select_dialog_singlechoice);
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
                .setTitle("Enter Supplier Details")
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
                            supplier = new Supplier();
                            supplier.setName(nameBox.getText().toString());
                            supplier.setAddress(addressBox.getText().toString());
                            supplier.setCompany(CompanyBox.getText().toString());
                            supplier.setEmail(emailBox.getText().toString());
                            supplier.setMobileNo(MobileNoBox.getText().toString());
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

    public void launchProgressDialog() {

        dialog = new MaterialDialog.Builder(this)
                .title("Progress")
                .content("Submitting, Please wait...")
                .progress(true, 0)
                .show();

    }

    public void dismissDialog() {

        if(dialog!=null){
            dialog.dismiss();
        }

    }

}
