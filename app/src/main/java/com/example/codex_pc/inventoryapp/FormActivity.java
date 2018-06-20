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
import android.widget.ListView;

import java.util.ArrayList;

public class FormActivity extends AppCompatActivity {

    ListView mainList;
    ArrayList<DynElement> elements;
    DynamicListAdapter adapter;

    int selection;
    private final static int RESULT_LOAD_IMG = 1;

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

        DynElement element11 = new DynElement("E");
        element11.setTitle("Supplier Name");
        element11.setInputTypeing(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);

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

}
