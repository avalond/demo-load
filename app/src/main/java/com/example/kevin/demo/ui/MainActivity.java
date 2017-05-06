package com.example.kevin.demo.ui;

import com.example.kevin.demo.R;
import com.example.kevin.demo.database.LokobeeDatabaseTable;
import com.example.kevin.demo.provider.LokobeeOrderProvider;
import com.example.kevin.demo.services.LokobeeService;
import com.example.kevin.demo.utils.LoggerUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.ComponentName;
import android.content.Context;

import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>, ServiceConnection {
  private static final String TAG = MainActivity.class.getSimpleName();
  private Intent intent;

  ///
  private RecyclerView mRecyclerView;
  private RecyclerView.LayoutManager mLayoutManager;
  private OrderItemAdapter mAdapter;

  private LokobeeService.myLocalIbind mLocalIbind = null;
  private static final int UPDATE_DATA = 0x12;
  private static final int GET_ALL_DATA = 0x2;
  private List<Map<String, String>> mOrderListMapList;

  //


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    initView();
    //
    mOrderListMapList = new ArrayList<>();
    // loader manger
    getSupportLoaderManager().initLoader(1, null, this);
  }


  private void initView() {
    mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
    //创建默认的线性LayoutManager
    mLayoutManager = new LinearLayoutManager(this);
    mRecyclerView.setLayoutManager(mLayoutManager);
    //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
    mRecyclerView.setHasFixedSize(true);
  }


  @Override public Loader<Cursor> onCreateLoader(int id, Bundle args) {
    ///

    CursorLoader loader = new CursorLoader(this, LokobeeOrderProvider.ORDER_CONTEXT_URI, null,
        LokobeeDatabaseTable.COLUMN_ID + "=? AND " + LokobeeDatabaseTable.COLUMN_ORDER_id + "=?",
        new String[] { LokobeeDatabaseTable.COLUMN_ID, LokobeeDatabaseTable.COLUMN_ORDER_id },
        LokobeeDatabaseTable.COLUMN_ORDER_id);
    return loader;
  }


  @Override public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
    if (cursor == null) {
      LoggerUtils.d(TAG, "Cursor is empty");
    } else {
      //set data and add to list view
      mOrderListMapList.clear();
      while (cursor.moveToNext()) {
        Map<String, String> map = new HashMap<>();
        String id = cursor.getString(cursor.getColumnIndex(LokobeeDatabaseTable.COLUMN_ID));
        String orderId = cursor.getString(cursor.getColumnIndex(LokobeeDatabaseTable.COLUMN_ORDER_id));
        String orderUUId = cursor.getString(cursor.getColumnIndex(LokobeeDatabaseTable.COLUMN_UUID));
        String orderType = cursor.getString(cursor.getColumnIndex(LokobeeDatabaseTable.COLUMN_TYPE));
        String orderStatus = cursor.getString(cursor.getColumnIndex(LokobeeDatabaseTable.COLUMN_ORDER_STATUS));
        String orderNote = cursor.getString(cursor.getColumnIndex(LokobeeDatabaseTable.COLUMN_ORDER_NOTE));

        map.put("id", id);
        map.put("orderId", orderId);
        map.put("orderUUId", orderUUId);
        map.put("orderType", orderType);
        map.put("orderStatus", orderStatus);
        map.put("orderNote", orderNote);
        mOrderListMapList.add(map);
        LoggerUtils.d(TAG, "mOrderListMapList size--->>" + mOrderListMapList.size());
      }
      //创建并设置Adapter
      mAdapter = new OrderItemAdapter(this, mOrderListMapList);
      mRecyclerView.setAdapter(mAdapter);
      mAdapter.notifyDataSetChanged();
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
