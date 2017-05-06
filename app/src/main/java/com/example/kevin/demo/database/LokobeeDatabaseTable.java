package com.example.kevin.demo.database;

import com.example.kevin.demo.utils.LoggerUtils;

import android.database.sqlite.SQLiteDatabase;

/**
 * @author by kevin.
 */

public class LokobeeDatabaseTable {
  private static final String TAG = LokobeeDatabaseTable.class.getSimpleName();
  // SQLite database table name
  private static final String ORDER_TABLE_NAME = "order_table";
  //
  //// order Colm
  public static final String COLUMN_ID = "_id";//sqlite id
  public static final String COLUMN_ORDER_id = "id";
  public static final String COLUMN_UUID = "uuid";
  public static final String COLUMN_TYPE = "type";
  public static final String COLUMN_ORDER_STATUS = "order_status";
  public static final String COLUMN_ORDER_NOTE = "order_note";

  //
  private static final String ORDER_TABLE_CRATE = "("
      + COLUMN_ID + "integer primary key autoincrement,"
      + COLUMN_ORDER_id + "text not null,"
      + COLUMN_UUID + "text,"
      + COLUMN_TYPE + "text,"
      + COLUMN_ORDER_STATUS + "text,"
      + COLUMN_ORDER_NOTE + "text,"
      + "UNIQUE(" + COLUMN_UUID + ")"
      + ");";

  //create table
  private static final String ORDER_TABLE_CREATE_STATEMENT = "create table" + ORDER_TABLE_NAME + ORDER_TABLE_CRATE;


  public static void onCreate(SQLiteDatabase db) {
    LoggerUtils.d(TAG, "DB onCreate ");
    db.execSQL(ORDER_TABLE_CREATE_STATEMENT);
  }


  public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    LoggerUtils.w(TAG, "Upgrading database from version " + oldVersion + " to " +
        newVersion + ", which will destroy all old data!");
    // drop table if is exits
    db.execSQL("DROP TABLE IF EXISTS " + ORDER_TABLE_NAME);

    //on create database
    onCreate(db);
  }
}
