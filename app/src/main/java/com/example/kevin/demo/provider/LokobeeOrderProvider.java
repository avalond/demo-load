package com.example.kevin.demo.provider;

import com.example.kevin.demo.database.DatabaseHelper;
import com.example.kevin.demo.utils.LoggerUtils;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * @author by kevin.
 */

public class LokobeeOrderProvider extends ContentProvider {
  private static final String TAG = LokobeeOrderProvider.class.getSimpleName();
  private DatabaseHelper mDatabaseHelper;
  private static final String AUTHORITY = "com.example.kevin.demo.provider";
  private static final Uri AUTHPRITY_URI = Uri.parse("content://" + AUTHORITY);

  //uri
  public static final String ORDER_TABLE_CONTENT_PATH = "order_details";
  public static final Uri ORDER_CONTEXT_URI = Uri.withAppendedPath(AUTHPRITY_URI, ORDER_TABLE_CONTENT_PATH);




  @Override public boolean onCreate() {
    mDatabaseHelper = new DatabaseHelper(getContext());
    LoggerUtils.d(TAG, "------" + TAG + "---->>db create");
    return (mDatabaseHelper != null);
  }


  @Nullable @Override
  public Cursor query(
      @NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
    return null;
  }


  @Nullable @Override public String getType(@NonNull Uri uri) {
    //  switch ()

    return null;
  }


  @Nullable @Override public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
    return null;
  }


  @Override public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
    return 0;
  }


  @Override public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
    return 0;
  }
}
