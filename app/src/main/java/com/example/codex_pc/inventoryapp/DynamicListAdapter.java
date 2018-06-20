package com.example.codex_pc.inventoryapp;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class DynamicListAdapter  extends ArrayAdapter<DynElement> {

    //Notice : VERYIMPORTANT: set (android:windowSoftInputMode="adjustPan") in the manifest of your formactivity
    // And also set the listview in that form to have android:descendantFocusability="beforeDescendants"

    private Context context;

    public DynamicListAdapter(Context context, ArrayList<DynElement> items) {
        super(context, 0,items);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;
        final DynElement item = getItem(position);
        int list_entry_xml;

        assert item != null;
        switch (item.getType()){
            case "E": list_entry_xml = R.layout.dynamic_edittext_list_item;
                break;

            case "T": list_entry_xml = R.layout.dynamic_title_list_item;
                break;

            case "L": list_entry_xml = R.layout.dynamic_line_list_item;
                break;

            case "t": list_entry_xml = R.layout.dynamic_subtext_list_item;
                break;

            case "S": list_entry_xml = R.layout.dynamic_selector_list_item;
                break;

            case "I": list_entry_xml = R.layout.dynamic_image_list_item;
                break;

            case "G": list_entry_xml = R.layout.dynamic_gap_list_item;
                break;

                default:list_entry_xml = R.layout.dynamic_line_list_item;
                break;
        }

        if (listItemView == null) {

            //Inflate to custom list_entry.xml
            listItemView = LayoutInflater.from(getContext()).inflate(
                    list_entry_xml, parent, false);

        }

        switch (item.getType()) {

            case "E":

                TextInputLayout til = listItemView.findViewById(R.id.dynamic_textInput_item);
                EditText et = listItemView.findViewById(R.id.dynamic_edittext_item);
                try {

                    if(item.getInputTypeing()!=0) {
                        et.setInputType(item.getInputTypeing());
                    }
                    til.setHint(item.getTitle());
                    et.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        }

                        @Override
                        public void afterTextChanged(Editable editable) {
                            item.setResult1(editable.toString());
                        }
                    });

                } catch (Exception e){
                    Log.d("Error Handler",e.toString());
                }

                break;

            case "T": {

                TextView tv = listItemView.findViewById(R.id.dynamic_title_item);
                try {
                    tv.setText(item.getTitle());
                    if (item.getBgSource() != 0) {
                        Drawable d = context.getResources().getDrawable(item.getBgSource());
                        tv.setBackground(d);
                    }
                    if (item.getColorSource() != 0) {
                        tv.setTextColor(item.getColorSource());
                    }
                    if (item.getIsBold() != 0) {
                        tv.setTypeface(Typeface.DEFAULT_BOLD);
                    }
                } catch (Exception e){
                    Log.d("Error Handler",e.toString());
                }

                break;
            }
            case "t": {

                TextView tv = listItemView.findViewById(R.id.dynamic_subtext_item);
                try {
                    tv.setText(item.getTitle());

                    if (item.getBgSource() != 0) {
                        Drawable d = context.getResources().getDrawable(item.getBgSource());
                        tv.setBackground(d);
                    }
                    if (item.getColorSource() != 0) {
                        tv.setTextColor(item.getColorSource());
                    }
                    if (item.getIsBold() != 0) {
                        tv.setTypeface(Typeface.DEFAULT_BOLD);
                    }

                } catch (Exception e){
                    Log.d("Error Handler",e.toString());
                }

                break;
            }
            case "S": {

                TextView tv = listItemView.findViewById(R.id.dynamic_selector_item);
                try {
                    tv.setText(item.getTitle());

                    if (item.getBgSource() != 0) {
                        Drawable d = context.getResources().getDrawable(item.getBgSource());
                        tv.setBackground(d);
                    }
                    if (item.getColorSource() != 0) {
                        tv.setTextColor(item.getColorSource());
                    }
                    if (item.getIsBold() != 0) {
                        tv.setTypeface(Typeface.DEFAULT_BOLD);
                    }

                } catch (Exception e){
                    Log.d("Error Handler",e.toString());
                }

                break;
            }

            case "G": {
                TextView tv = listItemView.findViewById(R.id.dynamic_gap_item);
                try {
                    tv.setHeight(item.getGapSize());
                } catch (Exception e){
                    Log.d("ErrorHandler",e.toString());
                }
                break;
            }

            case "L": {
                TextView tv = listItemView.findViewById(R.id.dynamic_line_item);
                try {
                    if(item.getBgSource()!=0){
                        Drawable d = context.getResources().getDrawable(item.getBgSource());
                        tv.setBackground(d);
                    }
                } catch (Exception e){
                    Log.d("ErrorHandler",e.toString());
                }

                break;
            }


            default:

                ImageView img = listItemView.findViewById(R.id.dynamic_image_item);
                try {
                    if (item.getImageURI() != null) {
                        img.setImageURI(item.getImageURI());
                    }
                    if (item.getImagePath() != null){
                        StorageReference ref = FirebaseStorage.getInstance().getReference().child(item.getImagePath());
                        GlideApp.with(context)
                                .load(ref)
                                .into(img);
                    }
                }   catch (Exception e){
                    Log.d("ErrorHandler",e.toString());
                }

                break;
        }

        return listItemView;

    }
}
