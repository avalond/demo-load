package com.example.kevin.demo.database;

import com.example.kevin.demo.utils.LoggerUtils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author by kevin.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
  private final String TAG = DatabaseHelper.class.getSimpleName();
  private static final String DATABASE_NAME = "lokobee.db";
  private static final int DATABASE_VERSION = 1;


  public DatabaseHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
    LoggerUtils.d(TAG, "--->>> DatabaseHelper super ");
  }


  @Override public void onCreate(SQLiteDatabase db) {
    LokobeeDatabaseTable.onCreate(db);
  }


  @Override public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    LokobeeDatabaseTable.onUpgrade(db, oldVersion, newVersion);
  }
}
