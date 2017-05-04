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


  public static void onCreate(SQLiteDatabase db) {
    LoggerUtils.d(TAG, "DB onCreate ");
    // db.execSQL();
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
