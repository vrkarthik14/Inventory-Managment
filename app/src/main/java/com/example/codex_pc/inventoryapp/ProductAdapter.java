package com.example.codex_pc.inventoryapp;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ProductAdapter extends ArrayAdapter<Product> {

    private Context context;
    private AppDatabase db;

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
        final Product currentItem = getItem(position);

        //Declare and assign all XML view elements from list_entry : Example
        TextView productName = listItemView.findViewById(R.id.productName);
        TextView productDesc = listItemView.findViewById(R.id.productDescription);
        TextView productQuantity = listItemView.findViewById(R.id.productQuantity);
        TextView productID = listItemView.findViewById(R.id.productID);
        final ImageView productIcon = listItemView.findViewById(R.id.productIcon);

        if (currentItem!=null) {
            productName.setText(currentItem.getName());
            productDesc.setText(currentItem.getDesc());
            productID.setText(currentItem.getID());
            String price = String.valueOf(currentItem.getPrice())+"/-";
            String quantity = String.valueOf(currentItem.getQuantity());
            productQuantity.setText(quantity);

            db = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, "image_drive").fallbackToDestructiveMigration().build();
            String imagePath = db.localImgDao().getImage(currentItem.getName()).getFilePath();

            if(imagePath!=null) {
                Uri imageUri = Uri.parse(imagePath);
                productIcon.setImageURI(imageUri);

                Log.d("ImageHandler","Local Storage");
            } else if(currentItem.getImagePath()!=null) {
                // Reference to an image file in Cloud Storage
                StorageReference storageReference = FirebaseStorage.getInstance().getReference().child(currentItem.getImagePath());

                try {
                    final File localFile = File.createTempFile("inventory", "jpg");
                    storageReference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            Uri imageUri = Uri.fromFile(localFile);
                            db.localImgDao().insertImage(new LocalImg(currentItem.getName(),imageUri.toString()));
                            productIcon.setImageURI(imageUri);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("ErrorHandler","Error getting file from firebase storage");
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.d("ImageHandler","FirebaseStorage");
            }
        }

        return listItemView;

    }
}
