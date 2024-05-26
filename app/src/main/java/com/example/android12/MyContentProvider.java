package com.example.android12;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;

import androidx.annotation.NonNull;

public class MyContentProvider extends ContentProvider {

    public static final String AUTHORITY = "com.example.android12.provider";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/data");

    private static final int DATA = 1;
    private static final UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, "data", DATA);
    }

    @Override
    public boolean onCreate() {
        return true;
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        if (uriMatcher.match(uri) == DATA) {
            MatrixCursor cursor = new MatrixCursor(new String[]{"_id", "name"});
            cursor.addRow(new Object[]{1, "John"});
            cursor.addRow(new Object[]{2, "Jane"});
            return cursor;
        }
        throw new IllegalArgumentException("Unsupported URI: " + uri);
    }

    @Override
    public String getType(@NonNull Uri uri) {
        if (uriMatcher.match(uri) == DATA) {
            return "vnd.android.cursor.dir/vnd.com.example.android12.data";
        }
        throw new IllegalArgumentException("Unsupported URI: " + uri);
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
