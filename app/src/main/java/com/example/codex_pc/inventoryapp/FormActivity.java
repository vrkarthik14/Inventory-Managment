package com.example.codex_pc.inventoryapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class FormActivity extends AppCompatActivity {

    ListView mainList;
    ArrayList<DynElement> elements;
    DynamicListAdapter adapter;

    int selection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        mainList = findViewById(R.id.mainList);

        selection = ((MyAppData)this.getApplication()).getSelection();

        elements = new ArrayList<>();

        if(selection==1) { elements = createSupplierOrCustomerForm("Supplier"); }
        else if (selection==2) { elements = createSupplierOrCustomerForm("Customer"); }

        adapter = new DynamicListAdapter(this, elements);
        mainList.setItemsCanFocus(true);
        mainList.setAdapter(adapter);

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

        if (selection==1){}

    }
}
