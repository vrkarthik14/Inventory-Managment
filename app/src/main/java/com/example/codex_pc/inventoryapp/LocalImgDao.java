package com.example.codex_pc.inventoryapp;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

@Dao
public interface LocalImgDao {

    @Query("SELECT * from localImg where productName = :productName")
    LocalImg getImage(String productName);

    @Insert(onConflict = IGNORE)
    void insertImage(LocalImg img);

}
