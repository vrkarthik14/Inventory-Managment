package com.example.codex_pc.inventoryapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.widget.ListView;

import java.util.ArrayList;

public class FormActivity extends AppCompatActivity {

    ListView mainList;
    ArrayList<DynElement> elements;
    DynamicListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        mainList = findViewById(R.id.mainList);

        elements = new ArrayList<>();

        DynElement element = new DynElement("T");
        element.setTitle("Enter Supplier Information");

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
        element5.setTitle("Supplier Company");
        element5.setInputTypeing(InputType.TYPE_CLASS_TEXT);

        elements.add(element);
        elements.add(element1);
        elements.add(element2);
        elements.add(element3);
        elements.add(element4);
        elements.add(element5);

        adapter = new DynamicListAdapter(this, elements);
        mainList.setItemsCanFocus(true);
        mainList.setAdapter(adapter);

    }
}
