package com.example.codex_pc.inventoryapp;

public class NavItem {

    private String title,desc;
    private int iconAddress;

    //==============================================================================================

        //Constructor

    public NavItem(String title, String desc, int iconAddress) {
        this.title = title;
        this.desc = desc;
        this.iconAddress = iconAddress;
    }

    //==============================================================================================

        //Getters

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }

    public int getIconAddress() {
        return iconAddress;
    }

    //==============================================================================================

        //Setters

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setIconAddress(int iconAddress) {
        this.iconAddress = iconAddress;
    }

    //==============================================================================================

}
