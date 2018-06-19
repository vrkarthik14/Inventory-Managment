package com.example.codex_pc.inventoryapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class NavigationAdapter extends ArrayAdapter<NavItem> {

    public NavigationAdapter(Context context, ArrayList<NavItem> items) {
        super(context, 0,items);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {

            //Inflate to custom list_entry.xml
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.nav_list_item, parent, false);

        }

        //Get Current CustomClass Object
        NavItem currentItem = getItem(position);

        //Declare and assign all XML view elements from list_entry : Example
        TextView Title = listItemView.findViewById(R.id.navTitle);
        TextView Desc = listItemView.findViewById(R.id.navDesc);
        ImageView Icon = listItemView.findViewById(R.id.navIcon);

        if (currentItem!=null) {
            Title.setText(currentItem.getTitle());
            Desc.setText(currentItem.getDesc());
            Icon.setImageResource(currentItem.getIconAddress());
        }

        return listItemView;

    }
}
