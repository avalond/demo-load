package com.example.kevin.demo.provider;

import com.example.kevin.demo.database.DatabaseHelper;
import com.example.kevin.demo.database.LokobeeDatabaseTable;
import com.example.kevin.demo.modle.Order;
import com.example.kevin.demo.utils.LoggerUtils;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

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
  public static final String ORDER_CONTENT_ITEM_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/order_details_one";

  //uri Matcher
  private static final int ORDER_TABLE_ALL = 1; //all
  private static final int ORDER_TABLE_ITEM_ONE = 2;//one
  private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);


  static {
    URI_MATCHER.addURI(AUTHORITY, ORDER_TABLE_CONTENT_PATH, ORDER_TABLE_ALL);
    URI_MATCHER.addURI(AUTHORITY, ORDER_TABLE_CONTENT_PATH + "/#", ORDER_TABLE_ITEM_ONE);
  }


  @Override public boolean onCreate() {
    mDatabaseHelper = new DatabaseHelper(getContext());
    LoggerUtils.d(TAG, "------" + TAG + "---->>db create");
    return (mDatabaseHelper != null);
  }


  public static ContentValues OrderContentValues(Order order) {
    ContentValues values = new ContentValues();
    values.put(LokobeeDatabaseTable.COLUMN_ORDER_ID, order.getId());
    values.put(LokobeeDatabaseTable.COLUMN_ORDER_STATUS, order.getOrderStatus());
    values.put(LokobeeDatabaseTable.COLUMN_ORDER_NOTE, order.getNote());
    values.put(LokobeeDatabaseTable.COLUMN_ORDER_EXPECT_TIME, order.getExpectTime());

    return values;
  }


  @Nullable @Override public String getType(@NonNull Uri uri) {
    switch (URI_MATCHER.match(uri)) {
      case ORDER_TABLE_ALL:
        return ORDER_CONTENT_TYPE;
      case ORDER_TABLE_ITEM_ONE:
        return ORDER_CONTENT_ITEM_TYPE;
      default:
        LoggerUtils.e(TAG, "get type Unsupported uri" + uri);
        throw new IllegalArgumentException("Unsupported uri" + uri);
    }
  }


  private long insertIfNotExists(SQLiteDatabase db, String tableName, ContentValues values) {
    long id;
    try {
      id = db.insertOrThrow(tableName, null, values);
    } catch (SQLiteConstraintException e) {
      id = -1;
      LoggerUtils.e(TAG, "insertIfNotExists function SQLiteConstraintException ---->>" + e.getLocalizedMessage());
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
      case ORDER_TABLE_ITEM_ONE:
        queryBuilder.setTables(LokobeeDatabaseTable.ORDER_TABLE_NAME);

        // add the Id to original query
        queryBuilder.appendWhere(LokobeeDatabaseTable.COLUMN_ID + "=" + uri.getLastPathSegment());
        break;
      default:
        LoggerUtils.e(TAG, "query Unknown URI : " + uri);
        throw new IllegalArgumentException("Unknown URI : " + uri);
    }
    SQLiteDatabase db = mDatabaseHelper.getReadableDatabase();
    Cursor cursor = queryBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
    cursor.setNotificationUri(getContext().getContentResolver(), uri);
    return cursor;
  }


  @Override public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
    int uriType = URI_MATCHER.match(uri);
    SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
    int rowsUpdated = 0;
    String id;
    switch (uriType) {
      case ORDER_TABLE_ALL:
        rowsUpdated = db.update(LokobeeDatabaseTable.ORDER_TABLE_NAME, values, selection, selectionArgs);
        break;
      case ORDER_TABLE_ITEM_ONE:
        id = uri.getLastPathSegment();
        if (TextUtils.isEmpty(selection)) {
          rowsUpdated = db.update(LokobeeDatabaseTable.ORDER_TABLE_NAME, values, LokobeeDatabaseTable.COLUMN_ID + "=" + id, null);
        } else {
          rowsUpdated = db.update(LokobeeDatabaseTable.ORDER_TABLE_NAME, values, LokobeeDatabaseTable.COLUMN_ID + "=" + id + "and" + selection,
              selectionArgs);
        }
        break;
      default:
        LoggerUtils.e(TAG, "update  Unknown URI : " + uri);
        throw new IllegalArgumentException("Unknown URI : " + uri);
    }
    getContext().getContentResolver().notifyChange(uri, null);
    return rowsUpdated;
  }


  @Nullable @Override public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
    SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
    Uri insertUri = null;
    long id;
    switch (URI_MATCHER.match(uri)) {
      case ORDER_TABLE_ALL:
        id = insertIfNotExists(db, LokobeeDatabaseTable.ORDER_TABLE_NAME, values);
        if (id >= 0) {
          insertUri = ContentUris.withAppendedId(ORDER_CONTEXT_URI, id);
          getContext().getContentResolver().notifyChange(uri, null, false);
        }
        break;
      default:
        LoggerUtils.e(TAG, "insert  Unsupported URI : " + uri);
        throw new IllegalArgumentException("Unsupported uri" + uri);
    }
    return insertUri;
  }


  @Override public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
    int uriType = URI_MATCHER.match(uri);
    SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
    int rowsDeleted = 0;
    String id;
    switch (uriType) {
      case ORDER_TABLE_ALL:
        rowsDeleted = db.delete(LokobeeDatabaseTable.ORDER_TABLE_NAME, selection, selectionArgs);
        break;
      case ORDER_TABLE_ITEM_ONE:
        id = uri.getLastPathSegment();
        if (TextUtils.isEmpty(selection)) {
          rowsDeleted = db.delete(LokobeeDatabaseTable.ORDER_TABLE_NAME, LokobeeDatabaseTable.COLUMN_ID + "=" + id, null);
        } else {
          rowsDeleted = db.delete(LokobeeDatabaseTable.ORDER_TABLE_NAME, LokobeeDatabaseTable.COLUMN_ID + "=" + id + " and " + selection,
              selectionArgs);
        }
        break;
      default:
        LoggerUtils.e(TAG, "delete Unknown Uri : " + uri);
        throw new IllegalArgumentException("Unknown Uri" + uri);
    }
    return rowsDeleted;
  }
}
