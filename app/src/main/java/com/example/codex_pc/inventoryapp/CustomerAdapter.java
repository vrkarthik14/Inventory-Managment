package com.example.codex_pc.inventoryapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomerAdapter extends ArrayAdapter<Customer> {

    public CustomerAdapter(Context context, ArrayList<Customer> items) {
        super(context, 0,items);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {

            //Inflate to custom list_entry.xml
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.supply_consume_list_item, parent, false);

        }

        //Get Current CustomClass Object
        Customer currentItem = getItem(position);

        //Declare and assign all XML view elements from list_entry : Example
        TextView Name = listItemView.findViewById(R.id.listName);
        TextView CompanyName = listItemView.findViewById(R.id.listCompanyName);
        TextView Email = listItemView.findViewById(R.id.listEmail);

        if (currentItem!=null) {
            Name.setText(currentItem.getName());
            CompanyName.setText(currentItem.getCompany());
            Email.setText(currentItem.getEmail());
        }

        return listItemView;

    }
}
