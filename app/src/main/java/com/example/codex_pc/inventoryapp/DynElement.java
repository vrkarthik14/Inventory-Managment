package com.example.codex_pc.inventoryapp;

import android.net.Uri;

import java.util.ArrayList;

public class DynElement {

    private String title,type,resulttype,result1;
    private ArrayList<String> entries;
    private int bgSource, colorSource, isBold, inputTypeing, result2, gapSize;
    private Uri imageURI;

    public DynElement(String type) {
        this.type = type;
        isBold = 0;
        inputTypeing = 0;
        colorSource = 0;
        bgSource = 0;
        imageURI = null;
        gapSize = 4;
        result1 = null;
        result2 = -1;
    }

    //==============================================================================================

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }

    public String getResulttype() {
        return resulttype;
    }

    public String getResult1() {
        return result1;
    }

    public ArrayList<String> getEntries() {
        return entries;
    }

    public int getBgSource() {
        return bgSource;
    }

    public int getColorSource() {
        return colorSource;
    }

    public int getIsBold() {
        return isBold;
    }

    public int getInputTypeing() {
        return inputTypeing;
    }

    public int getResult2() {
        return result2;
    }

    public Uri getImageURI() {
        return imageURI;
    }

    public int getGapSize() {
        return gapSize;
    }

    //==============================================================================================

    public void setTitle(String title) {
        this.title = title;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setResulttype(String resulttype) {
        this.resulttype = resulttype;
    }

    public void setResult1(String result1) {
        this.result1 = result1;
    }

    public void setEntries(ArrayList<String> entries) {
        this.entries = entries;
    }

    public void setBgSource(int bgSource) {
        this.bgSource = bgSource;
    }

    public void setColorSource(int colorSource) {
        this.colorSource = colorSource;
    }

    public void setIsBold(int isBold) {
        this.isBold = isBold;
    }

    public void setInputTypeing(int inputTypeing) {
        this.inputTypeing = inputTypeing;
    }

    public void setResult2(int result2) {
        this.result2 = result2;
    }

    public void setImageURI(Uri imageURI) {
        this.imageURI = imageURI;
    }

    public void setGapSize(int gapSize) {
        this.gapSize = gapSize;
    }

    //==============================================================================================
}
