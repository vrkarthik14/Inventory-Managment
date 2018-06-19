package com.example.codex_pc.inventoryapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class TransactionAdapter extends ArrayAdapter<Transaction> {

    public TransactionAdapter(Context context, ArrayList<Transaction> items) {
        super(context, 0,items);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {

            //Inflate to custom list_entry.xml
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.transaction_item, parent, false);

        }

        //Get Current CustomClass Object
        Transaction currentItem = getItem(position);

        //Declare and assign all XML view elements from list_entry : Example
        TextView ProductNameandProductID = listItemView.findViewById(R.id.listName);
        TextView quantity = listItemView.findViewById(R.id.listCompanyName);
        TextView SupplierOrCustomerName = listItemView.findViewById(R.id.listEmail);

        if (currentItem!=null) {
            String pnid = currentItem.getProductName() + "(" + currentItem.getProductID() + ")";
            String qty,name;
            if(currentItem.getIsSupply()==1) {
                qty = "Supplied " +String.valueOf(currentItem.getQuantity()) + " from";
                name = currentItem.getSupplier();
            } else {
                qty = "Issed " + String.valueOf(currentItem.getQuantity()) + " to";
                name = currentItem.getCustomer();
            }
            ProductNameandProductID.setText(pnid);
            quantity.setText(qty);
            SupplierOrCustomerName.setText(name);
        }

        return listItemView;

    }

}
