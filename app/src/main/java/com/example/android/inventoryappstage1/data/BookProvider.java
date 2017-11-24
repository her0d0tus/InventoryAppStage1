package com.example.android.inventoryappstage1.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

public class BookProvider extends ContentProvider {

    public static final String LOG_TAG = BookProvider.class.getSimpleName();

    private BookDbHelper mDbHelper;

    // int's for the UriMatcher
    private static final int BOOKS = 100;
    private static final int BOOK_ID = 101;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {

        sUriMatcher.addURI(BookContract.CONTENT_AUTHORITY, BookContract.PATH_BOOKS, BOOKS);
        sUriMatcher.addURI(BookContract.CONTENT_AUTHORITY, BookContract.PATH_BOOKS + "/#", BOOK_ID);
    }

    /**
     * Initialize provider and database helper object
     */
    @Override
    public boolean onCreate() {
        mDbHelper = new BookDbHelper(getContext());
        return true;
    }

    /**
     * Perform query for given URI. Use given projection, selection, selection arguments, and
     * sort order.
     */
    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        // Get readable database
        SQLiteDatabase database = mDbHelper.getReadableDatabase();

        // Cursor to hold results of query
        Cursor cursor;

        int match = sUriMatcher.match(uri);
        switch (match) {

            case BOOKS:
                // For the BOOKS code, query the books table with given projection, selection,
                // selection arguments, and sort order. The cursor could contain multiple rows of
                // books table.
                cursor = database.query(BookContract.BookEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case BOOK_ID:
                // extract ID from URI
                selection = BookContract.BookEntry._ID + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};

                cursor = database.query(BookContract.BookEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }

        return cursor;
    }

    /**
     * Insert new data with given contentvalues.
     */
    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        final int match = sUriMatcher.match(uri);

        switch (match) {
            case BOOKS:
                return insertBook(uri, contentValues);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    /**
     * Insert a book into the database with given content values. Return new content Uri
     * for that specific row in database.
     */
    private Uri insertBook(Uri uri, ContentValues contentValues) {

        String name = contentValues.getAsString(BookContract.BookEntry.COLUMN_BOOK_NAME);
        if (name == null) {
            throw new IllegalArgumentException("Book must have a name");
        }

        Float price = contentValues.getAsFloat(BookContract.BookEntry.COLUMN_BOOK_PRICE);
        if (price == null || price < 0) {
            throw new IllegalArgumentException("Book must have a valid price");
        }

        Integer quantity = contentValues.getAsInteger(BookContract.BookEntry.COLUMN_BOOK_QUANTITY);
        if (quantity == null || quantity < 0) {
            throw new IllegalArgumentException("Book must have a valid quantity");
        }

        String supplierName = contentValues.getAsString(BookContract.BookEntry.COLUMN_SUPPLIER_NAME);
        if (supplierName == null) {
            throw new IllegalArgumentException("Book must have the supplier's name");
        }

        String supplierEmail = contentValues.getAsString(BookContract.BookEntry.COLUMN_SUPPLIER_EMAIL);
        if (supplierEmail == null) {
            throw new IllegalArgumentException("Book must have the supplier's email");
        }

        String supplierNumber = contentValues.getAsString(BookContract.BookEntry.COLUMN_SUPPLIER_NUMBER);
        if (supplierNumber == null) {
            throw new IllegalArgumentException("Book must have the supplier's number");
        }

        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        long newRowId = database.insert(BookContract.BookEntry.TABLE_NAME, "", contentValues);

        if (newRowId == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }

        return ContentUris.withAppendedId(uri, newRowId);
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String selection, @Nullable String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);

        switch(match) {
            case BOOKS:
                return updateBook(uri, contentValues, selection, selectionArgs);
            case BOOK_ID:
                // update a single row
                selection = BookContract.BookEntry._ID + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                return updateBook(uri, contentValues, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }

    private int updateBook(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        if (values.size() == 0) {
            return 0;
        }

        if (values.containsKey(BookContract.BookEntry.COLUMN_BOOK_NAME)) {
            String name = values.getAsString(BookContract.BookEntry.COLUMN_BOOK_NAME);
            if (name == null) {
                throw new IllegalArgumentException("Book must have a name");
            }
        }

        if (values.containsKey(BookContract.BookEntry.COLUMN_BOOK_PRICE)) {
            Float price = values.getAsFloat(BookContract.BookEntry.COLUMN_BOOK_PRICE);
            if (price == null || price < 0) {
                throw new IllegalArgumentException("Book must have a valid price");
            }
        }

        if (values.containsKey(BookContract.BookEntry.COLUMN_BOOK_QUANTITY)) {
            Integer quantity = values.getAsInteger(BookContract.BookEntry.COLUMN_BOOK_QUANTITY);
            if (quantity == null || quantity < 0) {
                throw new IllegalArgumentException("Book must have a valid quantity");
            }
        }

        if (values.containsKey(BookContract.BookEntry.COLUMN_SUPPLIER_NAME)) {
            String supplierName = values.getAsString(BookContract.BookEntry.COLUMN_SUPPLIER_NAME);
            if (supplierName == null) {
                throw new IllegalArgumentException("Book must have the supplier's name");
            }
        }

        if (values.containsKey(BookContract.BookEntry.COLUMN_SUPPLIER_EMAIL)) {
            String supplierEmail = values.getAsString(BookContract.BookEntry.COLUMN_SUPPLIER_EMAIL);
            if (supplierEmail == null) {
                throw new IllegalArgumentException("Book must have the supplier's email");
            }
        }

        if (values.containsKey(BookContract.BookEntry.COLUMN_SUPPLIER_NUMBER)) {
            String supplierNumber = values.getAsString(BookContract.BookEntry.COLUMN_SUPPLIER_NUMBER);
            if (supplierNumber == null) {
                throw new IllegalArgumentException("Book must have the supplier's number");
            }
        }

        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        int rowsUpdated = database.update(BookContract.BookEntry.TABLE_NAME, values, selection, selectionArgs);

        return rowsUpdated;

    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        int rowsDeleted;

        final int match = sUriMatcher.match(uri);

        switch (match) {
            case BOOKS:
                // Delete all rows matching selection and selectionArgs
                rowsDeleted = database.delete(BookContract.BookEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case BOOK_ID:
                // Delete a single row of a given ID
                selection = BookContract.BookEntry._ID + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                rowsDeleted = database.delete(BookContract.BookEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }

        return rowsDeleted;
    }

    /**
     * Return the MIME type of data for content URI.
     */
    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final int match = sUriMatcher.match(uri);

        switch (match) {
            case BOOKS:
                return BookContract.BookEntry.CONTENT_LIST_TYPE;
            case BOOK_ID:
                return BookContract.BookEntry.CONTENT_BOOK_TYPE;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri + " with match " + match);
        }
    }
}
