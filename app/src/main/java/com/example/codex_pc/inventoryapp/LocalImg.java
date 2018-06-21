package com.example.codex_pc.inventoryapp;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class LocalImg {

    @PrimaryKey
    @NonNull
    public String id;

    public String productName;

    public String filePath;

    public LocalImg(String productName, String filePath) {
        this.productName = productName;
        this.filePath = filePath;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
