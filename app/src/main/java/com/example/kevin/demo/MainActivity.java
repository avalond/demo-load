package com.example.kevin.demo;

import com.example.kevin.demo.database.DatabaseHelper;
import com.example.kevin.demo.services.LokobeeService;
import com.example.kevin.demo.utils.LoggerUtils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>, ServiceConnection {
  private static final String TAG = MainActivity.class.getSimpleName();
  private Intent intent;
  private LokobeeService.myLocalIbind mLocalIbind = null;
  private static final int UPDATE_DATA = 0x12;
  private static final int GET_ALL_DATA = 0x2;



  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    //

    // loader manger
    getSupportLoaderManager().initLoader(1, null, this);
  }


  @Override public Loader<Cursor> onCreateLoader(int id, Bundle args) {
    ///
    // CursorLoader loader = new CursorLoader(this, LokobeeOrderProvider.ORDER_CONTEXT_URI, null, null, null);
    //  return loader;
    return null;
  }


  @Override public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
    if (data == null) {
      LoggerUtils.d(TAG, "Cursor is empty");
    } else {
      //set data and add to list view
    }
  }


  @Override public void onLoaderReset(Loader<Cursor> loader) {

  }


  @Override protected void onResume() {
    super.onResume();
    LoggerUtils.d(TAG, "onResume");
    intent = new Intent(MainActivity.this, LokobeeService.class);
    bindService(intent, this, Context.BIND_AUTO_CREATE);
    if (mLocalIbind != null) {
      mLocalIbind.getLokobeeService().getAllOrder(GET_ALL_DATA);
    }
  }


  @Override protected void onRestart() {
    super.onRestart();
    intent = new Intent(MainActivity.this, LokobeeService.class);
    bindService(intent, this, Context.BIND_AUTO_CREATE);
    LoggerUtils.d(TAG, "onRestart");
  }


  @Override protected void onStop() {
    super.onStop();
    intent = new Intent(MainActivity.this, LokobeeService.class);
    stopService(intent);
    unbindService(this);
    LoggerUtils.d(TAG, "onStop");
  }


  @Override protected void onDestroy() {
    super.onDestroy();
    stopService(intent);
    unbindService(this);
    LoggerUtils.d(TAG, "onDestroy");
  }


  @Override public void onServiceConnected(ComponentName name, IBinder service) {
    // services 链接上以后
    mLocalIbind = (LokobeeService.myLocalIbind) service;
    mLocalIbind.getLokobeeService().getAllOrder(GET_ALL_DATA);
  }


  @Override public void onServiceDisconnected(ComponentName name) {
    // service 断开连接
  }
}
