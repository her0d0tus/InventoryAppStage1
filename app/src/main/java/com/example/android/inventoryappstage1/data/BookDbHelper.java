package com.example.android.inventoryappstage1.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class BookDbHelper extends SQLiteOpenHelper {

    private static final String LOG_TAG = BookDbHelper.class.getSimpleName();
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "bookstore.db";

    // CREATE TABLE books (_id INTEGER PRIMARY KEY AUTOINCREMENT,product_name TEXT NOT NULL,
    //                      price REAL NOT NULL,quantity INTEGER NOT NULL,product_image TEXT,
    //                      supplier_name TEXT NOT NULL,supplier_email TEXT NOT NULL,
    //                      supplier_phone_number TEXT NOT NULL)
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + BookContract.BookEntry.TABLE_NAME + " (" +
                    BookContract.BookEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    BookContract.BookEntry.COLUMN_BOOK_NAME + " TEXT NOT NULL," +
                    BookContract.BookEntry.COLUMN_BOOK_PRICE + " REAL NOT NULL," +
                    BookContract.BookEntry.COLUMN_BOOK_QUANTITY + " INTEGER NOT NULL," +
                    BookContract.BookEntry.COLUMN_PRODUCT_IMAGE + " TEXT," +
                    BookContract.BookEntry.COLUMN_SUPPLIER_NAME + " TEXT NOT NULL," +
                    BookContract.BookEntry.COLUMN_SUPPLIER_EMAIL + " TEXT NOT NULL," +
                    BookContract.BookEntry.COLUMN_SUPPLIER_NUMBER + " TEXT NOT NULL)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + BookContract.BookEntry.TABLE_NAME;

    public BookDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // Database is at version 1.
    }
}
