package com.example.kevin.demo.provider;

import com.example.kevin.demo.database.DatabaseHelper;
import com.example.kevin.demo.database.LokobeeDatabaseTable;
import com.example.kevin.demo.modle.Order;
import com.example.kevin.demo.utils.LoggerUtils;
import java.util.List;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
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

  //order
  public static final String ORDER_CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/order_details";

  //uri Matcher
  private static final int ORDER_TABLE_ALL = 1;
  private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);


  static {
    URI_MATCHER.addURI(AUTHORITY, ORDER_TABLE_CONTENT_PATH, ORDER_TABLE_ALL);
  }


  @Override public boolean onCreate() {
    mDatabaseHelper = new DatabaseHelper(getContext());
    LoggerUtils.d(TAG, "------" + TAG + "---->>db create");
    return (mDatabaseHelper != null);
  }


  public static ContentValues OrderContentValues(List<Order> orderList) {
    ContentValues values = new ContentValues();
    for (Order order : orderList) {
      values.put(LokobeeDatabaseTable.COLUMN_ORDER_id, order.getId());
      values.put(LokobeeDatabaseTable.COLUMN_UUID, order.getUuid());
      values.put(LokobeeDatabaseTable.COLUMN_TYPE, order.getType());
      values.put(LokobeeDatabaseTable.COLUMN_ORDER_STATUS, order.getOrderStatus());
      values.put(LokobeeDatabaseTable.COLUMN_ORDER_NOTE, order.getNote());
    }
    return values;
  }


  @Nullable @Override public String getType(@NonNull Uri uri) {
    switch (URI_MATCHER.match(uri)) {
      case ORDER_TABLE_ALL:
        return ORDER_CONTENT_TYPE;
      default:
        LoggerUtils.e(TAG, "Unsupported uri" + uri);
        throw new IllegalArgumentException("Unsupported uri" + uri);
    }
  }


  private long insertIfNotExists(SQLiteDatabase db, String tableName, ContentValues values) {
    long id;
    try {
      id = db.insertOrThrow(tableName, null, values);
    } catch (SQLiteConstraintException e) {
      id = -1;
      LoggerUtils.e(TAG, "SQLiteConstraintException ---->>" + e.getLocalizedMessage());
    }
    return id;
  }


  @Nullable @Override
  public Cursor query(
      @NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
    SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

    switch (URI_MATCHER.match(uri)) {
      case ORDER_TABLE_ALL:
        queryBuilder.setTables(LokobeeDatabaseTable.ORDER_TABLE_NAME);
        break;
      default:
        LoggerUtils.e(TAG, "Unknown URI : " + uri);
        throw new IllegalArgumentException("Unknown URI : " + uri);
    }
    SQLiteDatabase db = mDatabaseHelper.getReadableDatabase();
    Cursor cursor = queryBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
    cursor.setNotificationUri(getContext().getContentResolver(), uri);
    return cursor;
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
