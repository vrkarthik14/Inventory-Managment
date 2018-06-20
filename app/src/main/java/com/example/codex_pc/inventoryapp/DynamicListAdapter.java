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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

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

        if (listItemView == null) {

            //Inflate to custom list_entry.xml
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.dynamicform_item, parent, false);

        }

        final DynElement item = getItem(position);

        assert item != null;
        switch (item.getType()) {

            case "E":

                TextInputLayout til = listItemView.findViewById(R.id.dynamic_textInput_item);
                EditText et = listItemView.findViewById(R.id.dynamic_edittext_item);
                et.setInputType(item.getInputTypeing());
                til.setVisibility(View.VISIBLE);
                til.setHint(item.getTitle());
                et.setVisibility(View.VISIBLE);

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

                break;

            case "T": {

                TextView tv = listItemView.findViewById(R.id.dynamic_title_item);
                tv.setVisibility(View.VISIBLE);
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

                break;
            }
            case "t": {

                TextView tv = listItemView.findViewById(R.id.dynamic_subtext_item);
                tv.setVisibility(View.VISIBLE);
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

                break;
            }
            case "S": {

                Spinner spinner = listItemView.findViewById(R.id.dynamic_spinner_item);
                spinner.setVisibility(View.VISIBLE);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                        context, android.R.layout.simple_spinner_item, item.getEntries());
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);

                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        item.setResult1(item.getEntries().get(i));
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                        Log.d("ErrorHandler", "Error occured in spinner");
                    }
                });

                TextView tv = listItemView.findViewById(R.id.dynamic_spinner_title);
                tv.setVisibility(View.VISIBLE);
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

                break;
            }

            case "G": {
                TextView tv = listItemView.findViewById(R.id.dynamic_gap_item);
                tv.setVisibility(View.VISIBLE);
                tv.setHeight(item.getGapSize());
                break;
            }

            case "L": {
                TextView tv = listItemView.findViewById(R.id.dynamic_line_item);
                tv.setVisibility(View.VISIBLE);
                if(item.getBgSource()!=0){
                    Drawable d = context.getResources().getDrawable(item.getBgSource());
                    tv.setBackground(d);
                }
                break;
            }


            default:

                ImageView img = listItemView.findViewById(R.id.dynamic_imageupload_item);
                img.setVisibility(View.VISIBLE);
                if (item.getImageURI() != null) {
                    img.setImageURI(item.getImageURI());
                }

                break;
        }

        return listItemView;

    }
}
