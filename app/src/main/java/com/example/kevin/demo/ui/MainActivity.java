package com.example.kevin.demo.ui;

import com.example.kevin.demo.R;
import com.example.kevin.demo.database.LokobeeDatabaseTable;
import com.example.kevin.demo.modle.Order;
import com.example.kevin.demo.provider.LokobeeOrderProvider;
import com.example.kevin.demo.services.LokobeeService;
import com.example.kevin.demo.ui.adapter.BaseCursorRecyclerViewAdapter;
import com.example.kevin.demo.ui.adapter.OrderCursorAdapter;
import com.example.kevin.demo.utils.LoggerUtils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>, ServiceConnection {
  private static final String TAG = MainActivity.class.getSimpleName();
  private Intent intent;

  ///
  private RecyclerView mRecyclerView;
  private LinearLayoutManager mLayoutManager;
  private OrderCursorAdapter mAdapter;

  private LokobeeService.myLocalIbind mLocalIbind = null;
  private static final int UPDATE_DATA = 0x12;
  private static final int GET_ALL_DATA = 0x2;
  private Cursor mCursor;
  //


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    initView();
    // loader manger
    getSupportLoaderManager().initLoader(1, null, this);
  }


  private void initView() {
    mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
    //创建默认的线性LayoutManager
    mLayoutManager = new LinearLayoutManager(this);
    mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
    mRecyclerView.setLayoutManager(mLayoutManager);
    //  mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
    mRecyclerView.setHasFixedSize(true);
    mAdapter = new OrderCursorAdapter(this, new OrderClickItem());
    mRecyclerView.setAdapter(mAdapter);
  }


  @Override public Loader<Cursor> onCreateLoader(int id, Bundle args) {
    ///

    CursorLoader loader = new CursorLoader(this,
        LokobeeOrderProvider.ORDER_CONTEXT_URI, //uri
        null,  // Projection
        null, //Selection
        null, //SelectionArgs
        null);// SortOrder

    /////
    LoggerUtils.e(TAG,
        "uri-->" + loader.getUri() + "--projection--" + loader.getProjection() + "--selection--" + loader.getSelection() + "--selectionArgs--" +
            loader.getSelectionArgs() + "--sortOrder--" + loader.getSortOrder());
    return loader;
  }


  @Override public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
    if (cursor == null) {
      LoggerUtils.d(TAG, "Cursor is empty");
    } else {
      //set data and add to list view
      mAdapter.changeCursor(cursor);
      mCursor = cursor;
    }
  }


  @Override public void onLoaderReset(Loader<Cursor> loader) {
    mAdapter.swapCursor(null);
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


  private class OrderClickItem implements BaseCursorRecyclerViewAdapter.ItemClickListener<OrderCursorAdapter.OrderCursorItemViewHolder> {

    @Override public void onClick(OrderCursorAdapter.OrderCursorItemViewHolder holder, View view, int position) {
      if (view == holder.mButton) {
        LoggerUtils.e(TAG, "click ");

        LoggerUtils.e(TAG, "mAdapter.getItemId(position);" + mAdapter.getItemId(position));

        LoggerUtils.e(TAG, "------>>" + mCursor.getString(mCursor.getColumnIndex((LokobeeDatabaseTable.COLUMN_ORDER_ID))));
        String orderId = "";
        mLocalIbind.getLokobeeService().deleteOrderWithId(orderId);
      }
    }


    @Override public void onItemClick(OrderCursorAdapter.OrderCursorItemViewHolder holder, View view, int position) {

    }
  }
}
