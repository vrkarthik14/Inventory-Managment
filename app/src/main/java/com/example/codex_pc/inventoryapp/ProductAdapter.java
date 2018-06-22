package com.example.codex_pc.inventoryapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class ProductAdapter extends ArrayAdapter<Product> {

    private Context context;

    public ProductAdapter(Context context, ArrayList<Product> items) {
        super(context, 0,items);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {

            //Inflate to custom list_entry.xml
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.product_list_item, parent, false);

        }

        //Get Current CustomClass Object
        Product currentItem = getItem(position);

        //Declare and assign all XML view elements from list_entry : Example
        TextView productName = listItemView.findViewById(R.id.productName);
        TextView productDesc = listItemView.findViewById(R.id.productDescription);
        TextView productQuantity = listItemView.findViewById(R.id.productQuantity);
        TextView productID = listItemView.findViewById(R.id.productID);
        ImageView productIcon = listItemView.findViewById(R.id.productIcon);

        if (currentItem!=null) {
            productName.setText(currentItem.getName());
            productDesc.setText(currentItem.getDesc());
            productID.setText(currentItem.getID());
            String price = String.valueOf(currentItem.getPrice())+"/-";
            String quantity = String.valueOf(currentItem.getQuantity());
            productQuantity.setText(quantity);

            if(currentItem.getImagePath()!=null) {
                // Reference to an image file in Cloud Storage
                StorageReference storageReference = FirebaseStorage.getInstance().getReference().child(currentItem.getImagePath());

                // Download directly from StorageReference using Glide
                // (See MyAppGlideModule for Loader registration)
                Glide.with(context)
                        .load(storageReference)
                        .into(productIcon);
            }
        }

        return listItemView;

    }
}
