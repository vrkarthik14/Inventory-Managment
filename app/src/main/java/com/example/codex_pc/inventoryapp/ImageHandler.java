package com.example.codex_pc.inventoryapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class ImageHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "LocalImageManager";
    private static final String TABLE_IMAGES = "images";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_FILE_PATH = "file_path";

    public ImageHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_IMAGES_TABLE = "CREATE TABLE " + TABLE_IMAGES + "(" +
                KEY_ID + "INTEGER PRIMARY KEY," + KEY_NAME + " TEXT," +
                KEY_FILE_PATH + " TEXT)";
        sqLiteDatabase.execSQL(CREATE_IMAGES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_IMAGES);
        onCreate(sqLiteDatabase);
    }

    public void addImagePath(localImg img){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, img.getProductName());
        values.put(KEY_FILE_PATH, img.getFilePath());

        // Inserting Row
        db.insert(TABLE_IMAGES, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }

    public String getImagePath(String productName){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                TABLE_IMAGES,
                new String[] { KEY_ID, KEY_NAME, KEY_FILE_PATH },
                KEY_NAME + "=?",
                new String[] { productName },
                null,
                null,
                null,
                null
        );

        if (cursor != null) {
            cursor.moveToFirst();
        }

        assert cursor != null;
        String imgPath = cursor.getString(2);

        cursor.close();
        // return contact
        return imgPath;
    }

    public ArrayList<String> getAllImagePaths() {
        ArrayList<String> imgPathList = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_IMAGES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                imgPathList.add(cursor.getString(2));
            } while (cursor.moveToNext());
        }

        cursor.close();

        // return contact list
        return imgPathList;
    }


    public int updateImagePath(localImg img) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, img.getProductName());
        values.put(KEY_FILE_PATH, img.getFilePath());

        // updating row
        return db.update(TABLE_IMAGES, values, KEY_NAME + " = ?",
                new String[] { img.getProductName() });
    }


    public void deleteImagePath(String productName) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_IMAGES, KEY_NAME + " = ?",
                new String[] { productName });
        db.close();
    }


    public int getLocalImagesCount() {
        String countQuery = "SELECT  * FROM " + TABLE_IMAGES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

}
