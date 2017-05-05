package com.example.kevin.demo;

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


  @Override protected void onStart() {
    super.onStart();
    intent = new Intent(MainActivity.this, LokobeeService.class);
    startService(intent);
  }


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    //
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
    bindService(new Intent(this, LokobeeService.class), this, Context.BIND_AUTO_CREATE);
  }


  @Override protected void onRestart() {
    super.onRestart();
    intent = new Intent(MainActivity.this, LokobeeService.class);
    startService(intent);
  }


  @Override protected void onStop() {
    super.onStop();

    stopService(new Intent(MainActivity.this, LokobeeService.class));
    unbindService(this);
  }


  @Override protected void onDestroy() {
    super.onDestroy();
    stopService(intent);
  }


  @Override public void onServiceConnected(ComponentName name, IBinder service) {

  }


  @Override public void onServiceDisconnected(ComponentName name) {

  }
}
