package com.example.codex_pc.inventoryapp;

public class localImg {

    private String productName;
    private String filePath;

    public localImg(String productName, String filePath) {
        this.productName = productName;
        this.filePath = filePath;
    }

    public String getProductName() {
        return productName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
