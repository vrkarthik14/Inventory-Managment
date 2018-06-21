package com.example.codex_pc.inventoryapp;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {LocalImg.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract LocalImgDao localImgDao();
}
