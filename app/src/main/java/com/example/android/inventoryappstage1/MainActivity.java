package com.example.android.inventoryappstage1;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.android.inventoryappstage1.data.BookContract;
import com.example.android.inventoryappstage1.data.BookDbHelper;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private BookDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDbHelper = new BookDbHelper(this);

        insertData();

        Cursor cursor = queryData();

        if (cursor.moveToFirst()) {

            int nameColumnIndex = cursor.getColumnIndex(BookContract.BookEntry.COLUMN_BOOK_NAME);
            int priceColumnIndex = cursor.getColumnIndex(BookContract.BookEntry.COLUMN_BOOK_PRICE);
            int quantityColumnIndex = cursor.getColumnIndex(BookContract.BookEntry.COLUMN_BOOK_QUANTITY);
            int supplierNameColumnIndex = cursor.getColumnIndex(BookContract.BookEntry.COLUMN_SUPPLIER_NAME);
            int supplierEmailColumnIndex = cursor.getColumnIndex(BookContract.BookEntry.COLUMN_SUPPLIER_EMAIL);
            int supplierNumberColumnIndex = cursor.getColumnIndex(BookContract.BookEntry.COLUMN_SUPPLIER_NUMBER);

            String name = cursor.getString(nameColumnIndex);
            Float price = cursor.getFloat(priceColumnIndex);
            Integer quantity = cursor.getInt(quantityColumnIndex);
            String supplierName = cursor.getString(supplierNameColumnIndex);
            String supplierEmail = cursor.getString(supplierEmailColumnIndex);
            String supplerNumber = cursor.getString(supplierNumberColumnIndex);

            Log.i(LOG_TAG, "Product Name: " + name + ", Price: " + String.valueOf(price)
                    + ", Quantity: " + String.valueOf(quantity) + ", Supplier Name: " + supplierName
                    + ", Supplier Email: " + supplierEmail + ", Supplier Number: " + supplerNumber);
        }

    }

    private void insertData() {

        ContentValues values = new ContentValues();

        values.put(BookContract.BookEntry.COLUMN_BOOK_NAME, "King Solomon's Mines");
        values.put(BookContract.BookEntry.COLUMN_BOOK_PRICE, "10.00");
        values.put(BookContract.BookEntry.COLUMN_BOOK_QUANTITY, "20");
        values.put(BookContract.BookEntry.COLUMN_SUPPLIER_NAME, "Books");
        values.put(BookContract.BookEntry.COLUMN_SUPPLIER_NUMBER, "123-456-7890");
        values.put(BookContract.BookEntry.COLUMN_SUPPLIER_EMAIL, "books@example.com");

        getContentResolver().insert(BookContract.BookEntry.CONTENT_URI, values);

    }

    private Cursor queryData() {

        SQLiteDatabase database = mDbHelper.getReadableDatabase();

        Cursor cursor = database.query(BookContract.BookEntry.TABLE_NAME, null, null, null, null, null, null);

        return cursor;
    }
}
